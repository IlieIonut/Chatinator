package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.tasks_layout.*
import java.lang.Exception

class TasksActivity : AppCompatActivity() {
    private val TAG = "TasksActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_layout)

        val projectsDataBase = FirebaseDatabase.getInstance().getReference("projects")
        val projectName = intent.getStringExtra("ProjectName")
        var specificProjectReference : DatabaseReference? = null
        val tasks = ArrayList<Task>()

//        val id : String? = specificProjectReference!!.push().key
//        val task = Task("Primul task",id!!,"7 days",false)
//        specificProjectReference!!.child(id).setValue(task)

        projectsDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (keyNode in dataSnapshot.children) {
                    val project = keyNode.getValue(Project::class.java)
                    if (project != null) {
                        if(project.name == projectName){
                            specificProjectReference = project.id.let { it -> projectsDataBase.child(it)}

                            Log.d(TAG,"Reference to specific project is $specificProjectReference")
                            specificProjectReference!!.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot){
                                    for(keyNode in dataSnapshot.children){
                                            Log.d(TAG,"Found task $keyNode")
                                        var task : Task? = null
                                        try {
                                                task = keyNode.getValue(Task::class.java)
                                            }catch (e : Exception){
                                                break
                                            }
                                            if (task != null) {
                                                tasks.add(task)
                                            }
                                        }
                                    val tasksAdapter = CustomAdapter(this@TasksActivity,R.layout.task_item,tasks,5)
                                    TasksListView.adapter = tasksAdapter
                                }

                                override fun onCancelled(error: DatabaseError) {
                                   Log.w(TAG,"Failed to read value.", error.toException())
                                }
                            })
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
