package com.example.chatinator

import android.provider.BaseColumns

object UsersContract {

    internal const val TABLE_NAME = "Users"

     object Columns{
         const val ID = BaseColumns._ID
         const val USERS_NAME = "Name"
         const val USERS_PASS = "Pass"
         const val USERS_EMAIL = "Email"
    }

}