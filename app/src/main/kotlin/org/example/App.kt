package org.example

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    DatabaseInitialiser.initialise()
}
