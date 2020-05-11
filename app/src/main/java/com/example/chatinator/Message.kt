package com.example.chatinator

class Message(id : String, message : String, date : String, time : String) : DatabaseElement("") {
    val id = id
    val message = message
    val date = date
    val time =  time

    constructor() : this("","","","") {}

    override fun toString(): String {
        return """
            message = $message
            date = $date
            time = $time
        """.trimIndent()
    }
}