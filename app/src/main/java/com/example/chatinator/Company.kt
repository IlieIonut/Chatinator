package com.example.chatinator

class Company : DatabaseElement() {
    var employees = 0
    var projects = 0

    override fun toString(): String {
        return """
               name = $name
               employees = $employees
               projects = $projects
          """.trimIndent()
    }
}