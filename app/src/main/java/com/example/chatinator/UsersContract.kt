package com.example.chatinator

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import java.net.URI

object UsersContract {

    internal const val TABLE_NAME = "Users"

    /**
     * The URI to access the Users table
     */
    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

     object Columns{
         const val ID = BaseColumns._ID
         const val USERS_NAME = "Name"
         const val USERS_PASS = "Pass"
         const val USERS_EMAIL = "Email"
         const val PROJECT_ID = "ProjectID"
    }

    fun getId(uri: Uri): Long{
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri{
        return ContentUris.withAppendedId(CONTENT_URI,id)
    }

}