package com.example.myapplication.ultis

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
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

fun log(tag: String = "asd", msg: String) {
    Log.d(tag, msg)
}