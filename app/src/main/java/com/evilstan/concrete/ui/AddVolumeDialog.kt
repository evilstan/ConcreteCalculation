package com.evilstan.concrete.ui

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.evilstan.concrete.ui.core.AddVolumeDialogListener
import com.evilstan.concrete.R


class AddVolumeDialog(
    private val addVolumeDialogListener: AddVolumeDialogListener,
    private val context: Context
) {

    fun makeDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.input_volume))

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        //TODO activate field on start
        val inputVolume = EditText(context)
        inputVolume.hint = context.resources.getString(R.string.volume)
        inputVolume.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        layout.addView(inputVolume)

        val inputDescription = EditText(context)
        inputDescription.hint = context.resources.getString(R.string.description)
        inputDescription.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(inputDescription)

        builder.setView(layout)

        builder.setPositiveButton(context.resources.getString(R.string.ok)) { _, _ ->
            val volume = inputVolume.text.toString()
            val description = inputDescription.text.toString()
            add(volume, description)
        }
        builder.setNegativeButton(context.resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
        inputVolume.requestFocus()

        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(inputVolume,InputMethod.SHOW_FORCED)
    }


    private fun add(volume: String, description: String) {
        try {
            addVolumeDialogListener.onAdd(volume.toDouble(), description)
        } catch (e: java.lang.NumberFormatException) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.wrong_volume),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }
}