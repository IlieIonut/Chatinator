package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.companies_layout.*

class CollaboratorActivity : AppCompatActivity() {

    private val TAG = "CollaboratorsActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collaborators_layout)


////
////        val collaboratorDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("collaborators")
////
////        ///TEMPORARY SOLUTION TO ADD COMPANY TO DATABASE
//////        Log.d(TAG,"project database reference is $companyDatabase")
//////
//////        val id : String? = companyDatabase.push().key
//////
//////        val company = Company(id!!,"Company",1,1)
//////
//////        companyDatabase.child(id).setValue(company)
////
////        val collaborators = ArrayList<Company>()
////
////        // Read from the database
////        collaboratorDatabase.addValueEventListener(object : ValueEventListener {
////            override fun onDataChange(dataSnapshot: DataSnapshot) {
////                // This method is called once with the initial value and again
////                // whenever data at this location is updated.
////
////                val keys = ArrayList<String>()
////
////                for (keyNode in dataSnapshot.children) {
////                    keyNode.key?.let { keys.add(it) }
////                    val collaborator = keyNode.getValue(Company::class.java)
////                    Log.d(TAG, "Read collaborator from databse:\n $collaborator")
////                    if (collaborator != null) {
////                        Log.d(TAG, "Added collaborator to collaborators array")
////                        collaborators.add(collaborator)
////                    }
////                }
////                Log.d(TAG, "Size of projects array is ${collaborators.size}")
////                val collaboratorAdapter = CustomAdapter(this@CollaboratorActivity, R.layout.collaborators_item, collaborators, 3)
////                companiesListView.adapter = collaboratorAdapter
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException())
////            }
////        })
////            val userAdapter = CustomAdapter(this, R.layout.collaborators_item, users,3)
////            collaboratorsListView.adapter = userAdapter
//        } }
    }
}