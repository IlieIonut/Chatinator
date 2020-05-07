package com.example.chatinator

class Company(id :  String, name : String, employees : Int, projects : Int) : DatabaseElement(name) {
    var employees = employees
    var projects = projects
    var id = id

    constructor() : this("","",0,0) {}

    override fun toString(): String {
        return """
               name = $name
               employees = $employees
               projects = $projects
          """.trimIndent()
    }
}