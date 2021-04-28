package com.mi.imaginatoprac.common.extension

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun EditText.setCustomInputFilters(length: Int) {
    this.filters = arrayOf(InputFilter { source, start, end, _, _, _ ->
        for (i in start until end) {
            if (!Character.isLetterOrDigit(source[i])) {
                return@InputFilter ""
            }
        }
        null
    }, InputFilter.LengthFilter(length))
}

fun TextInputLayout.removeErrorState() {
    isErrorEnabled = false
    error = null
}

fun EditText.setTextWatcher(relativeTextInputLayout: TextInputLayout) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.toString().checkNotEmpty()) {
                relativeTextInputLayout.error = null
            }
        }
    })
}