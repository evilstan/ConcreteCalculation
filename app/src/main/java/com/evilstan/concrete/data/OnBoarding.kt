package com.evilstan.concrete.data

class OnBoarding(private val tutorial1: String, private val tutorial2: String) : Concrete {
    override var totalVolume: Double = 0.0

    override fun dimensions() = tutorial1

    override fun volume() = tutorial2
}