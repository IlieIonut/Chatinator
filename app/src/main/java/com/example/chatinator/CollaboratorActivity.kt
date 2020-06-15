package com.example.chatinator

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.collaborators_layout.*
import kotlinx.android.synthetic.main.people_item.view.*

class CollaboratorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "CollaboratorActivity"
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.drawable.background)
        setContentView(R.layout.collaborators_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        mDrawerLayout = findViewById(R.id.collaboratorsLayout)
        mToggle = ActionBarDrawerToggle(this@CollaboratorActivity, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val collaboratorsDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Read from the database
        collaboratorsDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val users = ArrayList<User>()

                for (keyNode in dataSnapshot.children) {
                    val user = keyNode.getValue(User::class.java)
                    Log.d(TAG, "Read collaborator from database:\n $user")
                    if (user != null) {
                        Log.d(TAG, "Added collaborator to collaborator array")
                        users.add(user)
                    }
                }
                Log.d(TAG, "Size of users array is ${users.size}")
                val userAdapter = CustomAdapter(this@CollaboratorActivity, R.layout.people_item, users, 3)
                collaboratorsListView?.adapter = userAdapter

                collaboratorsListView.onItemClickListener = AdapterView.OnItemClickListener(){ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    val intent = Intent(this@CollaboratorActivity, ChatActivity::class.java)
                    intent.putExtra("ReceiverName",view1.people.text.toString())
                    startActivity(intent)
                }
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
            startActivity(Intent(this@CollaboratorActivity,Menu_Activity::class.java))
        }
        if(R.id.nav_exit==item.itemId) {
            mAuth.signOut()
            finish()
            startActivity(Intent(this@CollaboratorActivity,MainActivity::class.java))
        }
        return true
    }
}
