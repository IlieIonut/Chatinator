package com.example.chatinator

class User : DatabaseElement("user") {

    override fun toString(): String {
        return """
               name = $name
          """.trimIndent()
    }
}