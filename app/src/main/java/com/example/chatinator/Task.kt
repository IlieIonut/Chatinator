package com.example.chatinator

class Task(name : String, id : String, deadline : String, done : Boolean) : DatabaseElement(name){
    val id = id
    val deadline = deadline
    val done = done

    constructor() : this("","","", false) {}

    override fun toString(): String {
        return """
               $name
               $deadline
               $done
          """.trimIndent()
    }

}