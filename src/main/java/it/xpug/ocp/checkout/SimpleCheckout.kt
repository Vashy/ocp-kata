package it.xpug.ocp.checkout

interface Valuable {
    fun cost(): Int
}

class Product(
        val id: String,
        private val value: Int,
        private var discountThreshold: Int,
        private val discount: Int
) : Valuable {
    private var count = 0

    override fun cost(): Int {
            count++
            return if (count == discountThreshold) {
                count = 0
                value - discount
            } else
                value
        }
}

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

class ProductCheckout(private val products: List<Product>) : Checkout {
    override var total: Int = 0
        private set

    override fun scan(code: String) {
        total += products.find { it.id == code }?.cost() ?: throw IllegalArgumentException("Invalid code $code")
    }
}

object Factory {
    fun create(availableProducts: Map<String, Int> = mapOf()): Checkout {
        return if (availableProducts.isEmpty())
            SimpleCheckout()
        else
            MultiProductsCheckout(availableProducts)
    }

    fun create(products: List<Product>): Checkout {
        return ProductCheckout(products)
    }
}