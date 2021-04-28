package com.mi.imaginatoprac.common.extension

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.mi.imaginatoprac.ui.base.BaseViewModel

inline fun <reified VM : BaseViewModel> Fragment.initViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = this,
    factory: ViewModelProvider.Factory = defaultViewModelProviderFactory,
): VM {
    return ViewModelProvider(viewModelStoreOwner, factory)[VM::class.java]
}

fun Fragment.hideKeyboard() {
    val currentFocus = if (this is DialogFragment) dialog?.currentFocus else activity?.currentFocus
    val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}
