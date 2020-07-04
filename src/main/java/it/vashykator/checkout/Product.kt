package it.vashykator.checkout

interface Valuable {
    fun cost(): Int
}

interface Id {
    val id: String
}

interface Product : Valuable, Id