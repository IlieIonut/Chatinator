package com.example.chatinator

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.register_layout.*
import kotlinx.android.synthetic.main.projects_layout.*
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.log

private  const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.drawable.background)
        setContentView(R.layout.login_layout)

        LoginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0 : View?)
            {
                val name = usernameLogin.text.toString()
                val password = passwordLogin.text.toString()

                val cursor = contentResolver.query(UsersContract.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)
                cursor.use {
                    if (it != null) {
                        while(it.moveToNext()) {
                            // Cycle through all records
                            with(cursor) {
                                val nameDb = this?.getString(1)
                                val passDb = this?.getString(2)
                                if(name == nameDb && password == passDb)
                                {
                                    setContentView(R.layout.menu_layout)
//                                    window.setBackgroundDrawableResource(R.drawable.background)
//                                    val toolbar: Toolbar = findViewById(R.id.toolbar)
//                                    setSupportActionBar(toolbar)
//
//                                    val fab: FloatingActionButton = findViewById(R.id.fab)
//                                    fab.setOnClickListener { view ->
//                                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                            .setAction("Action", null).show()
//                                    }
//                                    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//                                    val navView: NavigationView = findViewById(R.id.nav_view)
//                                    val navController = findNavController(R.id.nav_host_fragment)
//                                    // Passing each menu ID as a set of Ids because each
//                                    // menu should be considered as top level destinations.
//                                    appBarConfiguration = AppBarConfiguration(setOf(
//                                        R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
//                                    setupActionBarWithNavController(navController, appBarConfiguration)
//                                    navView.setupWithNavController(navController)
                                }
                            }
                        }
                    }
                }
            }
        })

        RegisterButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0 : View?)
            {
                setContentView(R.layout.register_layout)

                ConfirmRegisterButton.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        val name = usernameRegister.text.toString()
                        val password = passwordRegister.text.toString()
                        val confirmPassword = passwordConfirm.text.toString()
                        val email = emailRegister.text.toString()

                        if(password == confirmPassword) {

                            var existsAlready = false

                            val cursor = contentResolver.query(
                                UsersContract.CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                            )

                            cursor.use {
                                if (it != null) {
                                    while (it.moveToNext()) {
                                        with(cursor) {
                                            val nameDb = this?.getString(1)
                                            if (name == nameDb) {
                                                existsAlready = true
                                            }
                                        }
                                    }
                                }
                            }

                            if (!existsAlready) {
                                val values = ContentValues().apply {
                                    put(UsersContract.Columns.USERS_NAME, name)
                                    put(UsersContract.Columns.USERS_PASS, password)
                                    put(UsersContract.Columns.USERS_EMAIL, email)
                                }

                                contentResolver.insert(UsersContract.CONTENT_URI, values)
                                setContentView(R.layout.activity_main)
                                window.setBackgroundDrawableResource(R.drawable.background)
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
                                appBarConfiguration = AppBarConfiguration(setOf(
                                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
                                setupActionBarWithNavController(navController, appBarConfiguration)
                                navView.setupWithNavController(navController)
                            }
                        }
                    }
                })

            }
        })
    }

    fun CompaniiClick (Button: View){
        setContentView(R.layout.companii_layout)

        val cursor = contentResolver.query(
                UsersContract.CONTENT_URI,
                null,
                null,
                null,
                null
        )

        cursor.use {
            if (it != null) {
                while (it.moveToNext()) {
                    with(cursor) {
                        val nameDb = this?.getString(1)
                        Toast.makeText(applicationContext, nameDb, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        Toast.makeText(applicationContext, "Companii Layout", Toast.LENGTH_SHORT).show()

    }

    fun ColaboratoriClick (Button: View){
        setContentView(R.layout.menu_layout)
    }

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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}