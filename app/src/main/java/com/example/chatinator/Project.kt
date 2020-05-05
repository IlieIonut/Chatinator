package com.example.chatinator

class Project : DatabaseElement() {
     var workers = 0
     var tasks = 0
     var company = 0

     override fun toString(): String {
          return """
               name = $name
               workers = $workers
               tasks = $tasks
               company = $company
          """.trimIndent()
     }
}