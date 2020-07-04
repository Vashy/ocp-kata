package it.vashykator.checkout

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