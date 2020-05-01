package com.example.chatinator

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
import android.view.View

val usersName = ArrayList<String>()
val usersPass = ArrayList<String>()

private  const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        val projection = arrayOf(UsersContract.Columns.USERS_NAME,UsersContract.Columns.USERS_EMAIL)
        val sortColumn = UsersContract.Columns.USERS_EMAIL

        val cursor = contentResolver.query(UsersContract.buildUriFromId(1),
        projection,
        null,
        null,
        sortColumn)
        Log.d(TAG,"********************************8")
        cursor.use {
            if (it != null) {
                while (it.moveToNext()) {
                    with(cursor){
//                        val id = this?.getLong(0)
                        val name = this?.getString(0)
//                        val pass = this?.getString(2)
                        val email = this?.getString(1)
                        val result = "Name: $name email: $email"
                        Log.d(TAG, "onCreate: reading data $result")
                    }
                }
            }
        }
        Log.d(TAG,"********************************8")

        usersName.add("Marcel")
        usersPass.add("parola")
        LoginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0 : View?)
            {
                val name = NameText.text.toString()
                val password = PasswordText.text.toString()

                if(name == usersName[0] && password == usersPass[0])
                {
                    setContentView(R.layout.activity_main)
                }
            }
        })

        RegisterButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0 : View?)
            {
                setContentView(R.layout.activity_main)
            }
        })
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
    }

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
}
