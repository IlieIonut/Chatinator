package com.example.chatinator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.projects_layout.*


class ProjectActivity : AppCompatActivity() {
    private val TAG = "ProjectActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.projects_layout)

        val projectsDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("projects")

        ///TEMPORARY SOLUTION TO ADD PROJECT TO DATABASE
//        Log.d(TAG,"project database reference is $projectsDatabase")
//
//        val id : String? = projectsDatabase.push().key
//
//        val project = Project(id!!,"Project",1,1,1)
//
//        projectsDatabase.child(id).setValue(project)

        val projects = ArrayList<Project>()

        // Read from the database
        projectsDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (keyNode in dataSnapshot.children) {
                    val project = keyNode.getValue(Project::class.java)
                    Log.d(TAG, "Read project from databse:\n $project")
                    if (project != null) {
                        Log.d(TAG, "Added project to projects array")
                        projects.add(project)
                    }
                }
                Log.d(TAG, "Size of projects array is ${projects.size}")
                val projectAdapter = CustomAdapter(this@ProjectActivity, R.layout.project_item, projects, 1)
                projectsListView.adapter = projectAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }
}


