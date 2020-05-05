package com.example.chatinator

class Company {
    var name = ""
    var employees = 0
    var projects = 0


    override fun toString(): String {
        return """
               name = $name
               workers = $employees
               tasks = $projects
          """.trimIndent()
    }
}