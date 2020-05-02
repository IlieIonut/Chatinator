package com.example.chatinator

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import kotlinx.android.synthetic.main.test.*
import kotlinx.android.synthetic.main.register.*
import android.view.View
import kotlin.math.log

private  const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //SA NU STERGI CHESTIILE ASTEA
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    //DE AICI IN JOS POTI SA FACI CE VREI



}



/*        val sortColumn = UsersContract.Columns.USERS_EMAIL

//        val cursor = contentResolver.query(UsersContract.buildUriFromId(1)

        LoginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0 : View?)
            {
                val name = NameText.text.toString()
                val password = PasswordText.text.toString()

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
                                    setContentView(R.layout.activity_main)
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
                setContentView(R.layout.register)

                ConfirmRegisterButton.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        val name = RegisterName.text.toString()
                        val password = RegisterPass.text.toString()
                        val email = RegisterEmail.text.toString()
                        var existsAlready = false
                        val cursor = contentResolver.query(UsersContract.CONTENT_URI,
                            null,
                            null,
                            null,
                            sortColumn)
                        cursor.use {
                            if (it != null) {
                                while (it.moveToNext()) {
                                    with(cursor){
                                        val nameDb = this?.getString(1)
                                        val passDb = this?.getString(2)
                                        if(name == nameDb && password == passDb)
                                        {
                                            existsAlready = true
                                        }
                                    }
                                }
                            }
                        }
                        Log.d(TAG,existsAlready.toString())
                        if(!existsAlready)
                        {
                            val values = ContentValues().apply{
                                put(UsersContract.Columns.USERS_NAME, name)
                                put(UsersContract.Columns.USERS_PASS, password)
                                put(UsersContract.Columns.USERS_EMAIL, email)
                            }

                            contentResolver.insert(UsersContract.CONTENT_URI, values)
                            setContentView(R.layout.activity_main)
                        }
                    }
                })

            }
        })*/
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//        val navView: NavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
//}
