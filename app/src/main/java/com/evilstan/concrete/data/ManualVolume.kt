package com.evilstan.concrete.data

data class ManualVolume(override var totalVolume: Double) : Concrete {
    var description = "Об'єм додано вручну"

    constructor(totalVolume: Double, description: String) : this(totalVolume) {
        if (description.isNotEmpty())
        this.description = description
    }

    override fun dimensions() = description

    override fun volume() = "Vзаг = ${out(totalVolume * 1000)} м³"

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun out(dimension: Double) = (dimension / 1000).format(2)

}
