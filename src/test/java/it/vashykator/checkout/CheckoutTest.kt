package it.vashykator.checkout

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

/*
Back to the Checkout
http://codekata.com/kata/kata09-back-to-the-checkout/

Item   Unit      Special
       Price     Price
--------------------------
  A     50       3 for 130
  B     30       2 for 45
  C     20
  D     15
*/
class CheckoutTest {
    private lateinit var checkout: SimpleCheckout

    @Before
    fun setUp() {
        checkout = SimpleCheckout()
    }

    @Test
    fun `one A should cost 50`() {
        val checkOut = Factory.create()

        assertEquals(0, checkOut.total)
        checkOut.scan("A")
        assertEquals(50, checkOut.total)
    }

    @Test
    fun `two A should cost 100`() {
        val checkOut = Factory.create()

        assertEquals(0, checkOut.total)
        checkOut.scan("A")
        assertEquals(50, checkOut.total)
        checkOut.scan("A")
        assertEquals(50 + 50, checkOut.total)
    }

    @Test
    fun `sum different units`() {
        val checkOut = Factory.create(mapOf("A" to 50, "B" to 30))

        checkOut.scan("A")
        assertEquals(50, checkOut.total)
        checkOut.scan("B")
        assertEquals(50 + 30, checkOut.total)
    }

    @Test
    fun `three kinds of items`() {
        val checkout = Factory.create(mapOf(
                "A" to 50,
                "B" to 30,
                "C" to 20,
        ))

        checkout.scan("A")
        assertEquals(50, checkout.total)
        checkout.scan("B")
        assertEquals(50 + 30, checkout.total)
        checkout.scan("C")
        assertEquals(50 + 30 + 20, checkout.total)
    }

    @Test
    fun `special offer`() {
        val checkout = Factory.create(listOf(
                DiscountableProduct("A", 50, 3, 20)
        ))
        checkout.scan("A")
        checkout.scan("A")
        checkout.scan("A")
        assertEquals(130, checkout.total)
        checkout.scan("A")
        assertEquals(130 + 50, checkout.total)
    }

    @Test
    fun `another special offer`() {
        val checkout = Factory.create(listOf(
                DiscountableProduct("B", 30, 2, 15)
        ))
        checkout.scan("B")
        checkout.scan("B")
        assertEquals(45, checkout.total)
        checkout.scan("B")
        checkout.scan("B")
        assertEquals(45 + 45, checkout.total)
    }

    @Test
    fun `this is difficult`() {
        // One more variation:
        // "E" costs 55.
        // But it costs just 19 if you have bought two of "C".
        // This is probably going to be difficult!
        val checkout = Factory.createComplexCheckout(55, 19, 20)
        checkout.scan("E")
        assertEquals(55, checkout.total)
        checkout.scan("C")
        assertEquals(55 + 20, checkout.total)
        checkout.scan("C")
        assertEquals(19 + 20 + 20, checkout.total)
    }
}