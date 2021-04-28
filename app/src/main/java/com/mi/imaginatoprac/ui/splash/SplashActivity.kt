package com.mi.imaginatoprac.ui.splash

import android.content.Intent
import com.basestructure.app.R
import com.mi.imaginatoprac.common.extension.initViewModel
import com.mi.imaginatoprac.common.extension.safeObserve
import com.mi.imaginatoprac.ui.base.BaseViewModelActivity
import com.mi.imaginatoprac.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseViewModelActivity<SplashViewModel>(R.layout.activity_splash) {

    override fun buildViewModel() = initViewModel<SplashViewModel>()

    override fun initLiveDataObservers() {
        super.initLiveDataObservers()
        with(viewModel) {
            sessionStateEvent.safeObserve(this@SplashActivity, ::handleSessionState)
        }
    }

    private fun handleSessionState(isLoggedId: Boolean) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
