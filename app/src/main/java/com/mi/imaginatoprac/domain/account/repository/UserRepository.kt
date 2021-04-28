package com.mi.imaginatoprac.domain.account.repository

import com.mi.imaginatoprac.data.account.entity.LoginResponse
import com.mi.imaginatoprac.domain.account.entity.User

interface UserRepository {
    suspend fun signInRequest(param: HashMap<String, String>): LoginResponse?
    suspend  fun getUserData(): User
    suspend fun isUserLoggedIn(): Boolean
}
