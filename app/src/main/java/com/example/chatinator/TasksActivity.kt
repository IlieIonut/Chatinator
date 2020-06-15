package com.example.chatinator

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.task_item.view.*
import kotlinx.android.synthetic.main.tasks_layout.*
import java.lang.Exception

class TasksActivity : AppCompatActivity() {
    private val TAG = "TasksActivity"
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_layout)


        mDrawerLayout = findViewById(R.id.taskLayout)
        mToggle = ActionBarDrawerToggle(this@TasksActivity, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val projectsDataBase = FirebaseDatabase.getInstance().getReference("projects")
        val usersDataBase = FirebaseDatabase.getInstance().getReference("users")
        val projectName = intent.getStringExtra("ProjectName")
        var specificProjectReference : DatabaseReference? = null
        var id : String? = null
        val userEmail = firebaseAuth.currentUser?.email

//        val id : String? = specificProjectReference!!.push().key
//        val task = Task("Primul task",id!!,"7 days",false)
//        specificProjectReference!!.child(id).setValue(task)

        usersDataBase.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                for(keyNode in datasnapshot.children)
                {
                    val user = keyNode.getValue(User::class.java)
                    if (user != null) {
                        if(user.email == userEmail) {

                            projectsDataBase.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val tasks = ArrayList<Task>()
                                    for (keyNode in dataSnapshot.children) {
                                        val project = keyNode.getValue(Project::class.java)
                                        if (project != null) {
                                            if(project.name == projectName){
                                                specificProjectReference = project.id.let { it -> projectsDataBase.child(it)}
                                                id = project.id

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
                                                        TasksListView.onItemClickListener = AdapterView.OnItemClickListener(){ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

                                                            for(task in tasks){
                                                                if(task.name == view1.taskTextView.text.toString())
                                                                {
                                                                    val usersDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
                                                                    val specificUserReference = usersDatabase.child(user.id)
                                                                    val usersTasksDatabase = specificUserReference.child("tasks")

                                                                    val savedTask = Task(task.name,task.id,task.deadline,task.done)

                                                                    usersTasksDatabase.child(task.id).setValue(savedTask)
                                                                    break
                                                                }
                                                            }

                                                        }
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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG,"Failed to read value.", error.toException())
            }
        })

        taskButton.setOnClickListener {
            val intent = Intent(this@TasksActivity, AddTaskActivity::class.java)
            intent.putExtra("ProjectReference",id)
            startActivity(intent)
        }
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
