package it.vashykator.checkout

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