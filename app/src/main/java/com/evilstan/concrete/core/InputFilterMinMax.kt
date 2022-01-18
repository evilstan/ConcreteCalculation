package com.evilstan.concrete.core

import android.text.InputFilter
import android.text.Spanned

class InputFilterMinMax(private val min: Double, private val max: Double) : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (
                    dest.subSequence(0, dstart).toString() +
                    source +
                    dest.subSequence(dend, dest.length)
                    ).toDouble()
            if (isInRange(min, max, input)) return null
        } catch (nfe: NumberFormatException) { }
        return ""
    }

    private fun isInRange(a: Double, b: Double, c: Double): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}