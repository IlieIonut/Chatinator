package com.example.chatinator

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login_layout.*


private  const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.drawable.background)
        setContentView(R.layout.login_layout)

    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        Log.d(TAG, "Current user is ${currentUser.toString()}")
        if (checkForInternetConnection()) {
            if (currentUser == null) {
                LoginButton.setOnClickListener {
                    val email = emailLogin.text.toString()
                    val password = passwordLogin.text.toString()

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            this@MainActivity
                        ) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val user: FirebaseUser? = firebaseAuth.currentUser
                                Log.d(TAG, "Current user is ${user.toString()}")
                                startActivity(Intent(this@MainActivity, Menu_Activity::class.java))
                                //                            updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                //updateUI(null)
                            }

                            // ...
                        }
                }

                RegisterButton.setOnClickListener {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            RegisterActivity::class.java
                        )
                    )
                }
            } else {
                startActivity(Intent(this@MainActivity, Menu_Activity::class.java))
            }
        }
}

    private fun checkForInternetConnection(): Boolean {

        val connectivityManager: ConnectivityManager = this
            .getSystemService(android.content.Context.
            CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isDeviceConnectedToInternet = networkInfo != null
                && networkInfo.isConnectedOrConnecting
        if (isDeviceConnectedToInternet) {

            return true

        } else {

            createAlert()
            return false
        }


    }


    private fun createAlert() {

        val alertDialog: AlertDialog =
            AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Network Error")
        alertDialog.setMessage("Please check your internet connection")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog: DialogInterface?, which: Int ->


            startActivity(Intent(Settings.ACTION_SETTINGS))

        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog: DialogInterface?, which: Int ->

            Toast.makeText(this@MainActivity,
                "You must be connected to the internet",
                Toast.LENGTH_SHORT).show()
            finish()
        }


        alertDialog.show()

    }

}