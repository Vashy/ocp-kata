package it.xpug.ocp.fizzbuzz

interface FizzBuzzGame {
    fun say(): String
}

object FizzBuzzFactory {
    fun create(number: Int): FizzBuzzGame {
        return object : FizzBuzzGame {
            override fun say(): String = number.toString()
        }
    }
}