package com.example.chatinator

class User : DatabaseElement() {

    override fun toString(): String {
        return """
               name = $name
          """.trimIndent()
    }
}