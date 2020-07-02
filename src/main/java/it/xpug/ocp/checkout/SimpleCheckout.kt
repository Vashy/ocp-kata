package it.xpug.ocp.checkout

class Product(
        val id: String,
        cost: Int,
        private var discountThreshold: Int,
        private val discount: Int
) {
    private var count = 0

    val cost = cost
        get() {
            count++
            return if (count == discountThreshold) {
                count = 0
                field - discount
            } else
                field
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
        total += products.find { it.id == code }?.cost ?: throw IllegalArgumentException("Invalid code $code")
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