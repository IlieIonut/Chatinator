package com.example.chatinator

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.companies_layout.*

class CompanyActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private val TAG = "CompanyActivity"
    private lateinit var mDrawerLayout: DrawerLayout
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var mToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.drawable.background)
        setContentView(R.layout.companies_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        mDrawerLayout = findViewById(R.id.companiesLayout)
        mToggle = ActionBarDrawerToggle(this@CompanyActivity, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                    Log.d(TAG, "Read company from database:\n $company")
                    if (company != null) {
                        Log.d(TAG, "Added company to companies array")
                        companies.add(company)
                    }
                }
                Log.d(TAG, "Size of companies array is ${companies.size}")
                val projectAdapter = CustomAdapter(this@CompanyActivity, R.layout.companies_item, companies, 2)
                companiesListView.adapter = projectAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(R.id.nav_home==item.itemId) {
            startActivity(Intent(this@CompanyActivity,Menu_Activity::class.java))
        }
        if(R.id.nav_exit==item.itemId) {
            mAuth.signOut()
            finish()
            startActivity(Intent(this@CompanyActivity,MainActivity::class.java))
        }
        return true
    }
}

