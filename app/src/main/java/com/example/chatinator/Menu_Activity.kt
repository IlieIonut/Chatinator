package com.example.chatinator

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.companii_layout.*
import kotlinx.android.synthetic.main.projects_layout.*

class Menu_Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fun projectClick (Button: View) {
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

                    val projectAdapter = ProjectsAdapter(this, R.layout.project_item, projects)
                    ListView.adapter = projectAdapter

                }
            }
        }

        fun collaboratorsClick (Button: View){
            setContentView(R.layout.menu_layout)
        }

        fun companyClick (Button: View){
            setContentView(R.layout.companii_layout)

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
            }

            if (companies.size!=0) {
                var company = Company()
                for (company in companies) {
                    companie1txt.setText(company.name)
                }
            }
            else {
                companie1txt.visibility = View.GONE
                companie2txt.visibility = View.GONE
                companie3txt.visibility = View.GONE
            }
            Toast.makeText(applicationContext, "Companii Layout", Toast.LENGTH_SHORT).show()

        }
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
}
