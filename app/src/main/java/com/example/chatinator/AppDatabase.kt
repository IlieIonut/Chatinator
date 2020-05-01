package com.example.chatinator

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.IllegalStateException

/**
 *Basic database class for the application
*This will be used only by the [AppProvider].
*/

private const val TAG = "AppDatabase"
private const val DATABASE_NAME = "Users.db"
private const val DATABASE_VERSION = 1

internal class AppDatabase private constructor(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    init {
        Log.d(TAG, "AppDatabase: initialising")
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG,"OnCreate: starts")
        val sSQL = """CREATE TABLE ${UsersContract.TABLE_NAME} (
            ${UsersContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL, 
            ${UsersContract.Columns.USERS_NAME} TEXT NOT NULL, 
            ${UsersContract.Columns.USERS_PASS} TEXT NOT NULL, 
            ${UsersContract.Columns.USERS_EMAIL} TEXT NOT NULL);""".replaceIndent(" ")
        Log.d(TAG, sSQL)
        db.execSQL(sSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: starts")
        when(oldVersion){
            1 ->{
                //upgrade logic from version 1
            }
            else -> throw IllegalStateException("onUpgrade() with unknown newVersion: $newVersion")
        }
    }

    companion object : SingletonHolder<AppDatabase, Context>(::AppDatabase)
}