package com.example.chatinator

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.companies_layout.*
import kotlinx.android.synthetic.main.projects_layout.*

class Menu_Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val TAG = "MenuActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_layout)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
////        setSupportActionBar(toolbar)
////
////        val fab: FloatingActionButton = findViewById(R.id.fab)
////        fab.setOnClickListener { view ->
////            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                .setAction("Action", null).show()
////        }
////        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
////        val navView: NavigationView = findViewById(R.id.nav_view)
////        val navController = findNavController(R.id.nav_host_fragment)
////        // Passing each menu ID as a set of Ids because each
////        // menu should be considered as top level destinations.
////        appBarConfiguration = AppBarConfiguration(
////            setOf(
////                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
////            ), drawerLayout
////        )
////        setupActionBarWithNavController(navController, appBarConfiguration)
////        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun projectClick(Button: View) {
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
                Log.d(TAG,"creating adapter for projects")
                val projectAdapter = CustomAdapter(this, R.layout.project_item, projects, 1)
                projectsListView.adapter = projectAdapter

            }
        }
    }

    fun collaboratorsClick(Button: View) {
        setContentView(R.layout.menu_layout)
    }

    fun companyClick(Button: View) {
        setContentView(R.layout.companies_layout)
        val cursor = contentResolver.query(
            CompaniesContract.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        var companies = ArrayList<Company>()

//        val values = ContentValues().apply {
//            put(CompaniesContract.Columns.COMPANIES_NAME, "SOFTBIN")
//            put(CompaniesContract.Columns.COMPANIES_EMPLOYEES, 15)
//            put(CompaniesContract.Columns.COMPANIES_PROJECT, 5)
//        }
//
//        contentResolver.insert(CompaniesContract.CONTENT_URI, values)

        cursor.use {
            if (it != null) {
                while (it.moveToNext()) {
                    with(cursor) {
                        val newCompany = Company()
                        newCompany.name = this?.getString(1).toString()
                        newCompany.employees = this?.getInt(2)!!
                        newCompany.projects = this?.getInt(3)
                        companies.add(newCompany)

                    }
                }
            }
            Log.d(TAG,"creating adapter for companies")
            val companyAdapter = CustomAdapter(this, R.layout.companies_item, companies,2)
            companiesListView.adapter = companyAdapter
        }
    }
}

