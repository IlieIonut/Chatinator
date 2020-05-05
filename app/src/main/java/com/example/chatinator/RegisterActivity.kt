package com.example.chatinator

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.register_layout.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        startActivity(Intent(this@RegisterActivity,Menu_Activity::class.java))
                    }
                }
            }
        })
    }
}
