package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chat_layout.*
import kotlinx.android.synthetic.main.projects_layout.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)

        val usersDataBase = FirebaseDatabase.getInstance().getReference("users")

        sendButton.setOnClickListener {
            Log.d(TAG,"button pressed")
            val message = messageInput.text.toString()

            if(!TextUtils.isEmpty(message))
            {
                val calendar : Calendar = Calendar.getInstance()
                var format : SimpleDateFormat = SimpleDateFormat("MMM dd, yyyy")
                val currentDate = format.format(calendar.time).toString()

                format = SimpleDateFormat("hh:mm a")
                val currentTime = format.format(calendar.time).toString()

                val senderUserEmail = firebaseAuth.currentUser?.email
                Log.d(TAG,"SenderUser Email is $senderUserEmail")
                val receiverUser = intent.getStringExtra("ReceiverName")
                Log.d(TAG,"Name of the receiver is $receiverUser")
                var receiverUserId  = ""
                var senderUserId  = ""
                var c = false

                // Read from the database
                usersDataBase.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if(c)
                        {
                            return
                        }

                        val keys = ArrayList<String>()
                        var a = false
                        var b = false
                        for (keyNode in dataSnapshot.children) {
                            keyNode.key?.let { keys.add(it) }
                            val user = keyNode.getValue(User::class.java)
                            Log.d(TAG, "Read user from databse:\n $user")
                            if (user != null) {
                                Log.d(TAG,"email of user in for is ${user.email}")
                                if(user.name == receiverUser)
                                {
                                    Log.d(TAG,"Successfully found the user with name $receiverUser")
                                    receiverUserId = user.id
                                    Log.d(TAG,"User with name $receiverUser has ID $receiverUserId")
                                    a = true
                                }
                                if(user.email == senderUserEmail)
                                {
                                    Log.d(TAG,"Successfully found the user with email $senderUserEmail")
                                    senderUserId = user.id
                                    Log.d(TAG,"User with email $senderUserEmail had ID $senderUserId")
                                    b = true
                                }
                                if(a && b)
                                {
                                    break
                                }
                            }
                        }
                        c = true
                        val senderUser = senderUserId.let { it -> usersDataBase.child(it) }
                        val receiverUser = receiverUserId.let { it -> usersDataBase.child(it) }
                        val messageReferenceInReceiverReference = senderUserId.let { it -> receiverUser.child(it) }
                        val messageReferenceInSenderReference = receiverUserId.let { it -> senderUser.child(it) }

                        val id : String? = messageReferenceInSenderReference.push().key
                        val message = Message(id!!,message,currentDate,currentTime)

                        messageReferenceInSenderReference.child(id).setValue(message)
                        messageReferenceInReceiverReference.child(id).setValue(message)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException())
                    }
                })
            }
        }
    }


}
