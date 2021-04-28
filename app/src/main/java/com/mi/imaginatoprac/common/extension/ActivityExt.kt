package com.mi.imaginatoprac.common.extension

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.mi.imaginatoprac.ui.base.BaseViewModel

inline fun <reified VM : BaseViewModel> FragmentActivity.initViewModel(
    factory: ViewModelProvider.Factory = defaultViewModelProviderFactory,
): VM {
    return ViewModelProvider(this, factory)[VM::class.java]
}

fun FragmentActivity.hideKeyboard() {
    val currentFocus = if (this is DialogFragment) dialog?.currentFocus else currentFocus
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}
