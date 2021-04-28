package com.mi.imaginatoprac.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.basestructure.app.R

abstract class BaseActivity(@LayoutRes layoutRes: Int) : AppCompatActivity(layoutRes) {

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    @CallSuper
    protected open fun initViews() {
    }


}
