package com.mi.imaginatoprac.ui.login

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.basestructure.app.BR
import com.mi.imaginatoprac.common.extension.checkNotEmpty
import com.mi.imaginatoprac.common.extension.setApiResponse
import com.mi.imaginatoprac.common.extension.setError
import com.mi.imaginatoprac.common.extension.setLoading
import com.mi.imaginatoprac.common.util.KeyUtils
import com.mi.imaginatoprac.common.util.ValidationUtils.isValidEmail
import com.mi.imaginatoprac.common.util.ValidationUtils.isValidPassword
import com.mi.imaginatoprac.data.account.entity.LoginResponse
import com.mi.imaginatoprac.domain.account.usecase.LoginUseCase
import com.mi.imaginatoprac.domain.base.UiState
import com.mi.imaginatoprac.ui.base.BaseViewModel
import com.mi.imaginatoprac.ui.base.SingleLiveEvent
import com.mi.imaginatoprac.ui.login.validation.LoginConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var loginUseCase: LoginUseCase
) : BaseViewModel() {


    @Bindable
    var email = ""
        set(value) {
            field = value
            checkValidation()
            notifyPropertyChanged(BR.email)
        }


    @Bindable
    var password = ""
        set(value) {
            field = value
            checkValidation(false)
            notifyPropertyChanged(BR.password)
        }

    @Bindable
    var enableLoginButton = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.enableLoginButton)
        }



    internal val validationLiveLiveEvent = SingleLiveEvent<LoginConstant>()

    private val _loginSingleLiveEvent = SingleLiveEvent<UiState<LoginResponse?>>()
    internal val loginSingleLiveEvent: LiveData<UiState<LoginResponse?>> = _loginSingleLiveEvent

    private fun checkValidation(isEmail : Boolean = true) {
        enableLoginButton = false
        when {
            isEmail && !email.checkNotEmpty() -> {
                validationLiveLiveEvent.value = LoginConstant.EMPTY_EMAIL
            }
            isEmail && !isValidEmail(email) -> {
                validationLiveLiveEvent.value = LoginConstant.INVALID_EMAIL
            }
            !isEmail && !password.checkNotEmpty() -> {
                validationLiveLiveEvent.value = LoginConstant.EMPTY_PASSWORD
            }
            !isEmail && !isValidPassword(password) -> {
                validationLiveLiveEvent.value = LoginConstant.INVALID_PASSWORD
            }
            else ->{
                enableLoginButton = true
                validationLiveLiveEvent.value = LoginConstant.NONE
            }
        }
    }


    fun performLogin() {
        _loginSingleLiveEvent.setLoading()

        val map = HashMap<String, String>().apply {
            put(KeyUtils.EMAIL, email)
            put(KeyUtils.PASSWORD, password)
        }
        loginUseCase.invoke(
            scope = viewModelScope,
            params = LoginUseCase.Param(map)
        ) {
            it.result(_loginSingleLiveEvent::setApiResponse, _loginSingleLiveEvent::setError)
        }

    }
}