package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.companies_layout.*

class CompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.companies_layout)

        val cursor = contentResolver.query(
            CompaniesContract.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        var companies = ArrayList<Company>()

//        val values = ContentValues().apply {
//            put(CompaniesContract.Columns.COMPANIES_NAME, "SOFTBIN")
//            put(CompaniesContract.Columns.COMPANIES_EMPLOYEES, 15)
//            put(CompaniesContract.Columns.COMPANIES_PROJECT, 5)
//        }
//
//        contentResolver.insert(CompaniesContract.CONTENT_URI, values)

        cursor.use {
            if (it != null) {
                while (it.moveToNext()) {
                    with(cursor) {
                        val newCompany = Company()
                        newCompany.name = this?.getString(1).toString()
                        newCompany.employees = this?.getInt(2)!!
                        newCompany.projects = this?.getInt(3)
                        companies.add(newCompany)

                    }
                }
            }
            val companyAdapter = CustomAdapter(this, R.layout.companies_item, companies,2)
            companiesListView.adapter = companyAdapter
        }
    }
}
