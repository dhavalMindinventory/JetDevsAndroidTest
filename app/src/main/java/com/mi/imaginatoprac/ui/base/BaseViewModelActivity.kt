package com.mi.imaginatoprac.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes

abstract class BaseViewModelActivity<VM : BaseViewModel>(
    @LayoutRes layoutRes: Int
) : BaseActivity(layoutRes) {

    val viewModel by lazy { buildViewModel() }

    protected abstract fun buildViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLiveDataObservers()
        viewModel.loadPage()
    }

    @CallSuper
    protected open fun initLiveDataObservers() = Unit
}
