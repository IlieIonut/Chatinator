package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProjectActivity : AppCompatActivity() {
    private val TAG = "ProjectActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.projects_layout)

        val projectsDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference("projects")
        Log.d(TAG,"project database reference is $projectsDatabase")

        val id : String? = projectsDatabase.push().key

        val project = Project(id!!,"Project",1,1,1)

        projectsDatabase.child(id).setValue(project)

//                val projectAdapter = CustomAdapter(this, R.layout.project_item, projects, 1)
//                projectsListView.adapter = projectAdapter

            }
        }


