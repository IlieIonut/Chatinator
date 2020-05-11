package com.example.chatinator

class User(id :  String, name : String, projects : Int, email : String) : DatabaseElement(name) {
    val id = id
    val projects = projects
    val email = email

    constructor() : this("","",0, "") {}

    override fun toString(): String {
        return """
               $name
          """.trimIndent()
    }
}