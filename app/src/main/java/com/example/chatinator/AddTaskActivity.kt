package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_task_layout.*

class AddTaskActivity : AppCompatActivity() {
    private val TAG = "AddTaskActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task_layout)

        addTask.setOnClickListener {
            val specificProjectId = intent.getStringExtra("ProjectReference")
            val projectsDataBase = FirebaseDatabase.getInstance().getReference("projects")
            val specificProjectReference = projectsDataBase.child(specificProjectId)

            val taskName = TaksName.text.toString()
            val taskDeadline = TaskDeadline.text.toString()

            if(!TextUtils.isEmpty(taskName) && !TextUtils.isEmpty(taskDeadline))
            {
                val id : String? = projectsDataBase.push().key
                val task = Task(taskName,id!!,taskDeadline,false)
                specificProjectReference.child(id).setValue(task)
                Log.d(TAG,"Successfully added a task")
                finish()
            }
        }
    }
}
