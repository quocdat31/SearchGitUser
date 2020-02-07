package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.view.*

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}

fun clearAndFocusEditText(textInputLayout: TextInputLayout) {
    textInputLayout.apply {
        if (editText?.text != null) {
            editText?.text?.clear()
        }
        searchEditText.isFocusableInTouchMode = true
        searchEditText.requestFocus()
    }
}

class DialogCreator(
    var context: Context,
    var msg: String,
    var positiveButtonText: String,
    var onClickPositiveButton: () -> Unit
) {

    lateinit var dialogCreator: DialogCreator

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