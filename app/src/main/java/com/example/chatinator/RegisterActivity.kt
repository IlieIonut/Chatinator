package com.example.chatinator

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.register_layout.*


class RegisterActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance();
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

                if(password == confirmPassword) {

                    if (mAuth != null) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this@RegisterActivity,
                                OnCompleteListener<AuthResult?> { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
//                                        val user: FirebaseUser? = mAuth.getCurrentUser()
                                        Log.d(TAG,"User registered")
                                        startActivity(Intent(this@RegisterActivity,Menu_Activity::class.java))
//                                        updateUI(user)
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        val message = task.exception.toString()
                                        Log.d(TAG,message)
                                    }

                                })
                    }
                }
            }
        })
    }
}
