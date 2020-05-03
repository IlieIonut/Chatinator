package com.example.chatinator

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object CompaniesContract {

    internal const val TABLE_NAME = "Companies"
    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, UsersContract.TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.${UsersContract.TABLE_NAME}"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.${UsersContract.TABLE_NAME}"

    object Columns{
        const val ID = BaseColumns._ID
        const val COMPANIES_NAME = "Name"
        const val COMPANIES_EMPLOYEES = "Employees"
        const val COMPANIES_PROJECT = "Project"
    }

    fun getId(uri: Uri): Long{
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(UsersContract.CONTENT_URI,id)
    }
}