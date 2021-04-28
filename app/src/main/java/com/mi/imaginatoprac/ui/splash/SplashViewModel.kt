package com.mi.imaginatoprac.ui.splash

import androidx.lifecycle.viewModelScope
import com.mi.imaginatoprac.domain.splash.usecase.SplashUseCase
import com.mi.imaginatoprac.ui.base.BaseViewModel
import com.mi.imaginatoprac.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase
) : BaseViewModel() {

    internal val sessionStateEvent = SingleLiveEvent<Boolean>()

    override fun loadPage(multipleTimes: Boolean): Boolean {
        navigateNextScreen()
        return super.loadPage(multipleTimes)
    }

    private fun navigateNextScreen() {
        val params = SplashUseCase.Params(TimeUnit.SECONDS.toMillis(3))
        splashUseCase.invoke(scope = viewModelScope, params = params) {
            it.result(sessionStateEvent::setValue) { throwable ->
                Timber.e(throwable)
            }
        }
    }
}
