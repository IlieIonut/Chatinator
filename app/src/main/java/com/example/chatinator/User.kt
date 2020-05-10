package com.example.chatinator

class User(id :  String, name : String, projects : Int) : DatabaseElement(name) {
    val id = id
    val projects = projects

    constructor() : this("","",0) {}

    override fun toString(): String {
        return """
               name = $name
          """.trimIndent()
    }
}