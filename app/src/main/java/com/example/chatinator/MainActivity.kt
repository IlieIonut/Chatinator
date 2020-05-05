package com.example.chatinator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*
import android.view.View

private  const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

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
                        while (it.moveToNext()) {
                            // Cycle through all records
                            with(cursor) {
                                val nameDb = this?.getString(1)
                                val passDb = this?.getString(2)
                                if (name == nameDb && password == passDb) {
                                   startActivity(Intent(this@MainActivity,Menu_Activity::class.java))
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
               startActivity(Intent(this@MainActivity,RegisterActivity::class.java))
            }
        })

        TODO("SEVCIUC: THE COMPANY,PROJECTS AND COLLABORATOR LAYOUT INCLUDING THEIR ITEM.XML FILES ARE JUST PROTOTYPES" +
                "U HAVE TO STYLE AND MODIFY THEM IN ORDER TO LOOK GOOD" +
                "AT THE MOMENT AFTER THE LOGIN/REGISTER U WILL BE REDIRECTED TO THE ACTIVITY_MAIN LAYOUT" +
                "IF U WANT TO TEST HOW IT CURRENTLY LOOKS GO IN MENU_ACTIVITY.KT" +
                "LINE 26 REPLACE ACTIVITY_MAIN WITH MENU_LAYOUT" +
                "COMMENT EVERYTHING FROM LINE 27 TO LINE 46 INCLUDING LINE 46" +
                "AFTER U MAKE THE DESIGN INFORM ME OR THEODORSON BECAUSE WE NEED TO MAKE THE CONNECTIONS BETWEEN LAYOUT AND CODE " +
                "***************************************************************************" +
                "REMINDER: COMPANIES_LAYOUT REPRESENT THE LIST OF COMPANIES, COMPANIES_ITEM REPRESENT THE LOOK OF THE ITEM IN THE LIST" +
                "***************************************************************************" +
                "BECAUSE OF THIS TODO THE PROGRAM WILL NOT RUN" +
                "COMMENT THE TODO BEFORE RUNNING THE PROGRAM")

    }
}