package com.example.chatinator

class Message(id : String, message : String, date : String, time : String) {
    val id = id
    val message = message
    val date = date
    val time =  time

    constructor() : this("","","","") {}

}