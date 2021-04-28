package com.mi.imaginatoprac.ui.home

import com.basestructure.app.R
import com.basestructure.app.databinding.FragmentHomeBinding
import com.mi.imaginatoprac.common.extension.initViewModel
import com.mi.imaginatoprac.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseViewModelFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {


    override fun buildViewModel() = initViewModel<HomeViewModel>()


    override fun getClassName(): String {
        return this::class.java.simpleName
    }
}