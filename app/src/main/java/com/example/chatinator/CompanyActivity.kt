package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.companies_layout.*
import kotlinx.android.synthetic.main.projects_layout.*

class CompanyActivity : AppCompatActivity() {

    private val TAG = "CompanyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.companies_layout)

        val companyDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("companies")

        ///TEMPORARY SOLUTION TO ADD COMPANY TO DATABASE
//        Log.d(TAG,"project database reference is $companyDatabase")
//
//        val id : String? = companyDatabase.push().key
//
//        val company = Company(id!!,"Company",1,1)
//
//        companyDatabase.child(id).setValue(company)

        val companies = ArrayList<Company>()

        // Read from the database
        companyDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val keys = ArrayList<String>()

                for (keyNode in dataSnapshot.children) {
                    keyNode.key?.let { keys.add(it) }
                    val company = keyNode.getValue(Company::class.java)
                    Log.d(TAG, "Read project from databse:\n $company")
                    if (company != null) {
                        Log.d(TAG, "Added project to projects array")
                        companies.add(company)
                    }
                }
                Log.d(TAG, "Size of projects array is ${companies.size}")
                val projectAdapter = CustomAdapter(this@CompanyActivity, R.layout.companies_item, companies, 2)
                companiesListView.adapter = projectAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

//
//            val companyAdapter = CustomAdapter(this, R.layout.companies_item, companies,2)
//            companiesListView.adapter = companyAdapter
        }
    }

