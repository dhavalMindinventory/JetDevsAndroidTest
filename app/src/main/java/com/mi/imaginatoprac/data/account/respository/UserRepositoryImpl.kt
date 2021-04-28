package com.mi.imaginatoprac.data.account.respository

import com.mi.imaginatoprac.common.util.KeyUtils.HTTP_SUCCESS
import com.mi.imaginatoprac.data.account.entity.LoginResponse
import com.mi.imaginatoprac.data.sharedprefs.SharedPrefs
import com.mi.imaginatoprac.domain.account.entity.User
import com.mi.imaginatoprac.domain.account.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val sharedPrefs: SharedPrefs
) : UserRepository {

    override suspend fun isUserLoggedIn(): Boolean {
        return sharedPrefs.isLoggedIn()
    }

    override suspend fun getUserData(): User {
        return userDao.getUser(sharedPrefs.userId)
    }

    override suspend fun signInRequest(param: HashMap<String, String>): LoginResponse? {
        val loginRes = userApi.login(param)
            loginRes.let { response ->
            val accountHeader = response.headers().get("X-Acc")
            if (!accountHeader.isNullOrEmpty()) {
                response.body().let {
                    if (it?.result == HTTP_SUCCESS) {
                        it.data?.user?.let{user->
                            sharedPrefs.userId = user.userId?.toString().orEmpty()
                            val user = User(user.userId?.toString().orEmpty(), accountHeader, user.userName.orEmpty())
                            userDao.insertReplace(user)
                        }
                    }else{
                        throw Exception(it?.errorMessage)
                    }
                }
            }else{
                throw Exception(loginRes.body()?.errorMessage)
            }
        }
        return loginRes.body()
    }
}