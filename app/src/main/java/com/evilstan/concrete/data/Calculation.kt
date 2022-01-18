package com.evilstan.concrete.data

data class Calculation(private val construction: Construction) {

    val constructions: MutableList<Construction>

    init {
        constructions = ArrayList()
        constructions.add(construction)
    }


    fun add(construction: Construction) {
        constructions.add(construction)
    }

    override fun toString(): String {
        val quantity = " шт"
        lateinit var result: String

        for (construction in constructions) {
            result = "" + construction.length + " x " +
                    construction.width + " x " +
                    construction.height + " x " +
                    construction.quantity + quantity
        }

        return result
    }
}