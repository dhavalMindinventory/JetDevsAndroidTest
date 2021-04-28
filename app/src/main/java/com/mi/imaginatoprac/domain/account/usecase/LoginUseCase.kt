package com.mi.imaginatoprac.domain.account.usecase

import com.mi.imaginatoprac.data.account.entity.LoginResponse
import com.mi.imaginatoprac.domain.account.repository.UserRepository
import com.mi.imaginatoprac.domain.base.BaseUseCase
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : BaseUseCase<LoginResponse, LoginUseCase.Param>() {
    data class Param(val data: HashMap<String, String>)

    override suspend fun execute(params: Param): LoginResponse {
        return userRepository.signInRequest(params.data)!!
    }
}
