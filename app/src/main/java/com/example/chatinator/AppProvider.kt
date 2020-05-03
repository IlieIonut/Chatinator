package com.example.chatinator

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

/**
 * This is the only class that know about [AppDatabase].
 */
private  const val TAG = "AppProvider"
const val CONTENT_AUTHORITY = "com.example.chatinator.provider"
private  const val USERS = 100
private const val USERS_ID = 101

private const val PROJECTS = 200
private const val PROJECTS_ID = 201

private const val COMPANIES = 300
private const val COMPANIES_ID = 301

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppProvider: ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }

    private fun buildUriMatcher() : UriMatcher {
        Log.d(TAG,"buildUriMatcher: starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        //e.g content://com.example.chatinator.provider/Users
        matcher.addURI(CONTENT_AUTHORITY,UsersContract.TABLE_NAME, USERS)
        //e.g content://com.example.chatinator.provider/Users/8
        matcher.addURI(CONTENT_AUTHORITY,"${UsersContract.TABLE_NAME}/#", USERS_ID)

        matcher.addURI(CONTENT_AUTHORITY,ProjectsContract.TABLE_NAME, PROJECTS)
        matcher.addURI(CONTENT_AUTHORITY,"${ProjectsContract.TABLE_NAME}/#", PROJECTS_ID)

        matcher.addURI(CONTENT_AUTHORITY,CompaniesContract.TABLE_NAME, COMPANIES)
        matcher.addURI(CONTENT_AUTHORITY,"${CompaniesContract.TABLE_NAME}/#", COMPANIES_ID)

        return matcher
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG,"insert: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG,"insert: match is $match")

        val recordId: Long
        val returnUri: Uri

        when(match){
            USERS ->{
                val db = AppDatabase.getInstance(context!!).writableDatabase
                recordId = db.insert(UsersContract.TABLE_NAME,null,values)
                if(recordId != -1L)
                {
                    returnUri = UsersContract.buildUriFromId(recordId)
                }
                else
                {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }
            PROJECTS ->{
                val db = AppDatabase.getInstance(context!!).writableDatabase
                recordId = db.insert(ProjectsContract.TABLE_NAME,null,values)
                if(recordId != -1L)
                {
                    returnUri = ProjectsContract.buildUriFromId(recordId)
                }
                else
                {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            COMPANIES ->{
                val db = AppDatabase.getInstance(context!!).writableDatabase
                recordId = db.insert(CompaniesContract.TABLE_NAME,null,values)
                if(recordId != -1L)
                {
                    returnUri = CompaniesContract.buildUriFromId(recordId)
                }
                else
                {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }
        Log.d(TAG,"Exciting insert, return $returnUri")
        return returnUri
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG,"query: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG,"query: match is $match")

        val queryBuilder: SQLiteQueryBuilder = SQLiteQueryBuilder()

        when (match) {
            USERS -> queryBuilder.tables = UsersContract.TABLE_NAME

            USERS_ID -> {
                queryBuilder.tables = UsersContract.TABLE_NAME
                val userId = UsersContract.getId(uri)
                queryBuilder.appendWhere("${UsersContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$userId")
            }

            PROJECTS -> queryBuilder.tables = ProjectsContract.TABLE_NAME

            PROJECTS_ID -> {
                queryBuilder.tables = ProjectsContract.TABLE_NAME
                val projectId = ProjectsContract.getId(uri)
                queryBuilder.appendWhere("${UsersContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$projectId")
            }

            COMPANIES -> queryBuilder.tables = CompaniesContract.TABLE_NAME

            COMPANIES_ID -> {
                queryBuilder.tables = CompaniesContract.TABLE_NAME
                val companyId = CompaniesContract.getId(uri)
                queryBuilder.appendWhere("${UsersContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$companyId")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(context!!).readableDatabase
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        Log.d(TAG, "query: rows in returned cursor = ${cursor.count}") // TODO remove this line

        return cursor
    }

    override fun onCreate(): Boolean {
        Log.d(TAG,"onCreate: starts")
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        val match = uriMatcher.match(uri)

        return when (match) {
            USERS -> UsersContract.CONTENT_TYPE

            USERS_ID -> UsersContract.CONTENT_ITEM_TYPE

            PROJECTS -> ProjectsContract.CONTENT_TYPE

            PROJECTS_ID -> ProjectsContract.CONTENT_ITEM_TYPE

            COMPANIES -> CompaniesContract.CONTENT_TYPE

            COMPANIES_ID -> CompaniesContract.CONTENT_ITEM_TYPE

            else -> throw IllegalArgumentException("unknown Uri: $uri")
        }
    }
}