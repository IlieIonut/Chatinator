package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show_tasks.*

class ShowTasksActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "ShowTasksActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tasks)

        val usersDataBase = FirebaseDatabase.getInstance().getReference("users")
        val userEmail = firebaseAuth.currentUser?.email
        var specificUserTasks : DatabaseReference? = null

        usersDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (keyNode in dataSnapshot.children) {
                    val user = keyNode.getValue(User::class.java)
                    Log.d(TAG, "Read user from databse:\n $user")
                    if (user != null) {
                        if (user.email == userEmail) {
                            specificUserTasks = usersDataBase.child(user.id).child("tasks")
                            specificUserTasks!!.addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val tasks = ArrayList<Task>()
                                    for (keyNode in dataSnapshot.children){
                                        val task = keyNode.getValue(Task::class.java)
                                        if (task != null)
                                        {
                                            Log.d(TAG, "Added task to tasks array")
                                            tasks.add(task)
                                        }
                                        Log.d(TAG, "Size of tasks array is ${tasks.size}")
                                        val projectAdapter = CustomAdapter(this@ShowTasksActivity, R.layout.task_item, tasks, 5)
                                        showTaskListView.adapter = projectAdapter
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
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


    }

}
