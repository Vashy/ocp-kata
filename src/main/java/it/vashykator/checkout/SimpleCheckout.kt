package it.vashykator.checkout

interface Valuable {
    fun cost(): Int
}

interface Id {
    val id: String
}

interface Product : Valuable, Id

class DiscountableProduct(
        override val id: String,
        private val value: Int,
        private var discountThreshold: Int,
        private val discount: Int
) : Product {
    private var boughtCount = 0

    override fun cost(): Int {
        boughtCount++
        return if (boughtCount == discountThreshold) {
            boughtCount = 0
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

    fun createComplexCheckout(ePrice: Int, eDiscountedPrice: Int, cPrice: Int): Checkout {
        return ComplexCheckout(ePrice, eDiscountedPrice, cPrice)
    }
}

class ComplexCheckout(private val ePrice: Int, private val eDiscountedPrice: Int, private val cPrice: Int) : Checkout {
    private val scannedProducts = mutableMapOf<String, Int>()

    override val total: Int
        get() {
            val isDiscounted = scannedProducts["C"] ?: 0 >= 2
            var sum = 0
            scannedProducts.forEach { (key, value) ->
                sum += when {
                    key == "E" && isDiscounted -> eDiscountedPrice * value
                    key == "E" -> ePrice * value
                    key == "C" -> cPrice * value
                    else -> 0
                }
            }
            return sum
        }

    override fun scan(code: String) {
        scannedProducts.merge(code, 1) { _, value -> value + 1 }
    }

}
