package it.vashykator.checkout

interface Checkout {
    val total: Int
    fun scan(code: String)
}