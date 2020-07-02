package it.xpug.ocp.checkout

interface Checkout {
    val total: Int
    fun scan(code: String)
}

class SimpleCheckout : Checkout {
    override var total: Int = 0
        private set

    override fun scan(code: String) {
        this.total += 50
    }
}

class TwoProductsCheckout : Checkout {
    override var total: Int = 0
        private set

    override fun scan(code: String) {
        when (code) {
            "A" -> total += 50
            "B" -> total += 30
        }
    }
}

class MultiProductsCheckout(private val availableProducts: Map<String, Int>) : Checkout {
    override var total: Int = 0
        private set

    override fun scan(code: String) {
        total += availableProducts[code] ?: 0
    }
}

object Factory {
    fun create(availableProducts: Map<String, Int> = mapOf()): Checkout {
        return if (availableProducts.isEmpty())
            SimpleCheckout()
        else
            MultiProductsCheckout(availableProducts)
    }
}