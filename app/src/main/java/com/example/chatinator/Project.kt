package com.example.chatinator

class Project (id :  String, name : String, workers : Int, tasks : Int,companies : Int) : DatabaseElement(name){
     val workers = workers
     val tasks = tasks
     val companies = companies
     val id = id

    constructor() : this("","",0,0,0) {}

     override fun toString(): String {
          return """
               name = $name
               workers = $workers
               tasks = $tasks
               company = $companies
          """.trimIndent()
     }
}