package com.mi.imaginatoprac.common.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar


fun View.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View?.showSnackBar(message: String?, icon: Int = 0) {
    this ?: return
    if (!message.isNullOrEmpty()) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
            show()
        }
    }
}

fun View?.showSnackBar(message: Int, icon: Int = 0) {
    this ?: return
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
        show()
    }
}
