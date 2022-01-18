package com.evilstan.concrete.core

import com.evilstan.concrete.data.Concrete

class VolumeCalculator{

    fun calculate(dataset:List<Concrete>): String {
        var volume = 0.0
        for (concrete in dataset) {
            volume += concrete.totalVolume
        }

        return volume.format(2)
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

}