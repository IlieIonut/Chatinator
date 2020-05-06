package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.collaborators_layout.*

class CollaboratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collaborators_layout)

        val cursor = contentResolver.query(
            UsersContract.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val users = ArrayList<User>()

        cursor.use {
            if (it != null) {
                while (it.moveToNext()) {
                    with(cursor) {
                        val newUser = User()
                        newUser.name = this?.getString(1).toString()
                        users.add(newUser)
                    }
                }
            }
            val userAdapter = CustomAdapter(this, R.layout.collaborators_item, users,3)
            collaboratorsListView.adapter = userAdapter
        }
    }
}
