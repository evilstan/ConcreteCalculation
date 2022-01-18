package com.evilstan.concrete.core

import android.content.Context
import com.evilstan.concrete.R
import com.evilstan.concrete.data.Concrete
import com.evilstan.concrete.data.OnBoarding

class Tutorial(private val context: Context) {
    private var dataset:MutableList<Concrete> = ArrayList()

    fun makeTutorial():MutableList<Concrete> {
        dataset.add(
            OnBoarding(
                context.resources.getString(R.string.tutorial1),
                context.resources.getString(R.string.tutorial2)
            )
        )
        dataset.add(
            OnBoarding(
                context.resources.getString(R.string.tutorial3),
                context.resources.getString(R.string.tutorial4)
            )
        )
        dataset.add(
            OnBoarding(
                context.resources.getString(R.string.tutorial5),
                context.resources.getString(R.string.tutorial6)
            )
        )
        dataset.add(
            OnBoarding(
                context.resources.getString(R.string.tutorial7),
                context.resources.getString(R.string.tutorial8)

            )
        )
        return dataset
    }
}