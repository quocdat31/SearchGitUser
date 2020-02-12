package com.example.myapplication.ultis

import android.app.AlertDialog
import android.content.Context

class DialogCreator(
    var context: Context,
    var msg: String,
    var positiveButtonText: String,
    var onClickPositiveButton: () -> Unit
) {
    fun build() {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(
                positiveButtonText
            ) { _, _ -> onClickPositiveButton() }
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ -> dialog?.dismiss() }
            .create()
            .show()
    }
}
