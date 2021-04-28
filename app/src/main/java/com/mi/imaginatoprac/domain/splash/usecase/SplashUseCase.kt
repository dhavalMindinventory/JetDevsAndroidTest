package com.mi.imaginatoprac.domain.splash.usecase

import com.mi.imaginatoprac.domain.account.repository.UserRepository
import com.mi.imaginatoprac.domain.base.BaseUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Boolean, SplashUseCase.Params>() {

    data class Params(val delayInMillis: Long)

    override suspend fun execute(params: Params): Boolean {
        delay(params.delayInMillis)
        return userRepository.isUserLoggedIn()
    }
}
