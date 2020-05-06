package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.projects_layout.*

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.projects_layout)

        val cursor = contentResolver.query(
            ProjectsContract.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val projects = ArrayList<Project>()

        cursor.use {
            if (it != null) {
                while (it.moveToNext()) {
                    // Cycle through all records
                    with(cursor) {
                        val newProject = Project()
                        newProject.name = this?.getString(1).toString()
                        newProject.workers = this?.getInt(2)!!
                        newProject.tasks = this.getInt(3)
                        newProject.company = this.getInt(4)
                        projects.add(newProject)
                    }
                }
                val projectAdapter = CustomAdapter(this, R.layout.project_item, projects, 1)
                projectsListView.adapter = projectAdapter

            }
        }
    }
}
