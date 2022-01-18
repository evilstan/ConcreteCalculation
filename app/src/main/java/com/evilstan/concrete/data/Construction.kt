package com.evilstan.concrete.data

data class Construction(
    val length: Double,
    val width: Double,
    val height: Double,
    val quantity: Int,
) : Concrete {

    override var totalVolume: Double = length / 1000 * width / 1000 * height / 1000 * quantity

    override fun dimensions() =
        "Конструкція ${out(length)} x ${out(width)} " + "x ${out(height)} м, $quantity шт"

    override fun volume() = "Vзаг = ${out(totalVolume * 1000)} м³"

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun out(dimension: Double) = (dimension / 1000).format(2)

    override fun toString() = "${dimensions()}, ${volume()}"
}