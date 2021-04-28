package com.mi.imaginatoprac.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class BaseViewModelFragment<VM : BaseViewModel, T : ViewDataBinding>(
    @LayoutRes layoutRes: Int
) : BaseFragment<T>(layoutRes) {

    protected val viewModel by lazy { buildViewModel() }

    protected abstract fun buildViewModel(): VM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveDataObservers()
        viewModel.loadPage(isMultipleLoad())
    }

    @CallSuper
    protected open fun initLiveDataObservers() {
    }

    protected open fun isMultipleLoad(): Boolean = false
}
