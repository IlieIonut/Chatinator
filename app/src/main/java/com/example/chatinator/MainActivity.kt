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
    }
}