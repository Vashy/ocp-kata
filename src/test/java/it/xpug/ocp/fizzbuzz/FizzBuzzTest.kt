package it.xpug.ocp.fizzbuzz

import org.junit.Test
import kotlin.test.assertEquals

class FizzBuzzTest {
    @Test
    fun `given a number should return that number`() {
        val fizzBuzzGame = FizzBuzzFactory.create(1)

        assertEquals("1", fizzBuzzGame.say())
    }

    @Test
    fun `given 3 it should return Fizz`() {
        val fizzBuzzGame = FizzBuzzFactory.create(3)

        assertEquals("Fizz", fizzBuzzGame.say())
    }
}