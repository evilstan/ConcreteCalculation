package com.evilstan.concrete.ui

import android.app.AlertDialog
import android.content.Context
import com.evilstan.concrete.ui.core.ClearDialogListener
import com.evilstan.concrete.R

class ClearListDialog(
    private val onDialogActionListener: ClearDialogListener,
    private val context: Context
) {
    fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.ask_clear_all))

        builder.setPositiveButton(context.resources.getString(R.string.yes)) { _, _ ->
            onDialogActionListener.onClear()
        }
        builder.setNegativeButton(context.resources.getString(R.string.no)) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}