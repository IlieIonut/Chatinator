package com.example.chatinator

class Project (private val id :  String, name : String, workers : Int, tasks : Int,companies : Int) : DatabaseElement(name){
     val workers = workers
     val tasks = tasks
     val companies = companies

     override fun toString(): String {
          return """
               name = $name
               workers = $workers
               tasks = $tasks
               company = $companies
          """.trimIndent()
     }
}