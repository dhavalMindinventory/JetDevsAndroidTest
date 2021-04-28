package com.mi.imaginatoprac.ui.login

import androidx.navigation.fragment.findNavController
import com.mi.imaginatoprac.ui.login.validation.LoginConstant
import com.basestructure.app.R
import com.basestructure.app.databinding.FragmentLoginBinding
import com.mi.imaginatoprac.common.extension.*
import com.mi.imaginatoprac.common.util.ProgressDialogUtil
import com.mi.imaginatoprac.data.account.entity.LoginResponse
import com.mi.imaginatoprac.domain.base.UiState
import com.mi.imaginatoprac.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    BaseViewModelFragment<LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {


    override fun buildViewModel() = initViewModel<LoginViewModel>()

    override fun initLiveDataObservers() {
        super.initLiveDataObservers()
        with(viewModel) {
            validationLiveLiveEvent.safeObserve(viewLifecycleOwner, ::handleValidations)
            loginSingleLiveEvent.safeObserve(viewLifecycleOwner, ::handleSignInResponse)
        }
    }

    override fun initViews() {
        super.initViews()
        binding.loginViewModel = viewModel
    }

    // Handle Error message on text input
    private fun handleValidations(loginConstant: LoginConstant) {
        with(binding) {
            when (loginConstant) {
                LoginConstant.EMPTY_EMAIL -> {
                    tilEmail.isErrorEnabled = true
                    tilEmail.error =
                        resources.getString(R.string.enter_email)
                }
                LoginConstant.EMPTY_PASSWORD -> {
                    tiPassword.isErrorEnabled = true
                    tiPassword.error =
                        resources.getString(R.string.enter_password)
                }
                LoginConstant.INVALID_EMAIL -> {
                    tilEmail.isErrorEnabled = true
                    tilEmail.error =
                        resources.getString(R.string.msg_invalid_email)
                }
                LoginConstant.INVALID_PASSWORD -> {
                    tiPassword.isErrorEnabled = true
                    tiPassword.error =
                        resources.getString(R.string.msg_password)
                }
                LoginConstant.NONE ->{
                    tilEmail.removeErrorState()
                    tiPassword.removeErrorState()
                }
            }
        }
    }

    // handle response of api in loading , error and success state
    private fun handleSignInResponse(response: UiState<LoginResponse?>) {
        binding.apply {
            tilEmail.removeErrorState()
            tiPassword.removeErrorState()
        }
        when (response) {
            is UiState.Loading -> {
                context?.let(ProgressDialogUtil::showProgressDialog)
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(getString(R.string.msg_login_success))
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(response.throwable.message)
            }
        }
    }


    override fun getClassName(): String {
        return this::class.java.simpleName
    }
}