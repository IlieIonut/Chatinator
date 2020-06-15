package com.example.chatinator

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.project_item.view.*
import kotlinx.android.synthetic.main.projects_layout.*

class ProjectActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "ProjectActivity"
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.drawable.background)
        setContentView(R.layout.projects_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        mDrawerLayout = findViewById(R.id.projectLayout)
        mToggle = ActionBarDrawerToggle(this@ProjectActivity, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val projectsDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("projects")

        ///TEMPORARY SOLUTION TO ADD PROJECT TO DATABASE
//        Log.d(TAG,"project database reference is $projectsDatabase")
//
//        val id : String? = projectsDatabase.push().key
//
//        val project = Project(id!!,"Project",1,1,1)
//
//        projectsDatabase.child(id).setValue(project)


        // Read from the database
        projectsDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val projects = ArrayList<Project>()

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
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(R.id.nav_home==item.itemId) {
            startActivity(Intent(this@ProjectActivity,Menu_Activity::class.java))
        }
        if(R.id.nav_exit==item.itemId) {
            mAuth.signOut()
            finish()
            startActivity(Intent(this@ProjectActivity,MainActivity::class.java))
        }
        return true
    }


    fun projectItemClick(Button : View) {
        val intent = Intent(this@ProjectActivity, TasksActivity::class.java)
        intent.putExtra("ProjectName",Button.ProjectButton.text.toString())
        Log.d(TAG,"Name of the project is ${Button.ProjectButton.text}")
        startActivity(intent)
    }

}


