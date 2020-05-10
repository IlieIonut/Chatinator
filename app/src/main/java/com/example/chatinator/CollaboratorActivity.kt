package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.collaborators_layout.*
import kotlinx.android.synthetic.main.projects_layout.*

class CollaboratorActivity : AppCompatActivity() {
    private val TAG = "CollaboratorActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collaborators_layout)

        val collaboratorsDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        val users = ArrayList<User>()

        // Read from the database
        collaboratorsDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val keys = ArrayList<String>()

                for (keyNode in dataSnapshot.children) {
                    keyNode.key?.let { keys.add(it) }
                    val user = keyNode.getValue(User::class.java)
                    Log.d(TAG, "Read user from database:\n $user")
                    if (user != null) {
                        Log.d(TAG, "Added project to projects array")
                        users.add(user)
                    }
                }
                Log.d(TAG, "Size of users array is ${users.size}")
                val userAdapter = CustomAdapter(this@CollaboratorActivity, R.layout.collaborators_item, users, 3)
                collaboratorsListView.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
