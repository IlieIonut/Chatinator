package com.example.chatinator

class Company(employees : Int, projects : Int) : DatabaseElement("name") {
    var employees = employees
    var projects = projects

    override fun toString(): String {
        return """
               name = $name
               employees = $employees
               projects = $projects
          """.trimIndent()
    }
}