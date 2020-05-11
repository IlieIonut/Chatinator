package com.example.chatinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.chat_layout.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)

        val usersDataBase = FirebaseDatabase.getInstance().getReference("users")
        val senderUserEmail = firebaseAuth.currentUser?.email
        val receiverUserName = intent.getStringExtra("ReceiverName")
        var receiverUserId  = ""
        var senderUserId = ""
        var firstCondition = false
        var secondCondition = false
        var c = false

        usersDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(c)
                {
                    return
                }
                for (keyNode in dataSnapshot.children) {
                    val user = keyNode.getValue(User::class.java)
                    Log.d(TAG, "Read user from databse:\n $user")
                    if (user != null) {
                        if(user.email == senderUserEmail)
                        {
                            Log.d(TAG,"Successfully found the user with email $senderUserEmail")
                            senderUserId = user.id
                            Log.d(TAG,"User with email $senderUserEmail had ID $senderUserId")
                            firstCondition = true
                        }
                        if(user.name == receiverUserName)
                        {
                            Log.d(TAG,"Successfully found the user with email $senderUserEmail")
                            receiverUserId = user.id
                            Log.d(TAG,"User with email $senderUserEmail had ID $senderUserId")
                            secondCondition = true
                        }
                        if(firstCondition && secondCondition)
                        {
                            Log.d(TAG,"SenderUserID is $senderUserId")
                            Log.d(TAG,"ReceiverUserID is $receiverUserId")
                            val messageDatabase = usersDataBase.child(senderUserId).child(receiverUserId)
                            Log.d(TAG,"Reference to messages is $messageDatabase")
                            messageDatabase.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.

                                    val messages = ArrayList<Message>()

                                    for (key in dataSnapshot.children) {
                                        val message = key.getValue(Message::class.java)
                                        if(message != null)
                                        {
                                            Log.d(TAG,"Found message is $message")
                                            messages.add(message)
                                        }
                                    }
                                    Log.d(TAG,"Size of messages array is ${messages.size}")
                                    val messageAdapter = CustomAdapter(this@ChatActivity, R.layout.chat_item, messages, 4)
                                    messagesListView.adapter = messageAdapter
                                    c = true
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



            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

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

                Log.d(TAG,"SenderUser Email is $senderUserEmail")
                Log.d(TAG,"Name of the receiver is $receiverUserName")
                val senderUser = senderUserId.let { it -> usersDataBase.child(it) }
                val receiverUser = receiverUserId.let { it -> usersDataBase.child(it) }
                val messageReferenceInReceiverReference = senderUserId.let { it -> receiverUser.child(it) }
                val messageReferenceInSenderReference = receiverUserId.let { it -> senderUser.child(it) }

                val id : String? = messageReferenceInSenderReference.push().key
                val message = Message(id!!,message,currentDate,currentTime)

                messageReferenceInSenderReference.child(id).setValue(message)
                messageReferenceInReceiverReference.child(id).setValue(message)

            }
        }
    }


}
