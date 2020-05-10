package com.example.chatinator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.register_layout.*


class RegisterActivity : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    private val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        Log.d(TAG,"Started")
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG,"Started")

        ConfirmRegisterButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val password = passwordRegister.text.toString()
                val confirmPassword = passwordConfirm.text.toString()
                val email = emailRegister.text.toString()
                val username = usernameRegister.text.toString()

                if(password == confirmPassword) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@RegisterActivity
                        ) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val user: FirebaseUser? = firebaseAuth.currentUser
                                Log.d(TAG,"User registered, currentUser is $user")
                                val usersDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

                                Log.d(TAG,"users database reference is $usersDatabase")

                                val id : String? = usersDatabase.push().key

                                val savedUser = User(id!!,username,0)

                                usersDatabase.child(id).setValue(savedUser)
                                startActivity(Intent(this@RegisterActivity,Menu_Activity::class.java))
                    //                                        updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                val message = task.exception.toString()
                                Log.d(TAG,message)
                            }

                        }
                }
            }
        })
    }
}
