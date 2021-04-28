package com.mi.imaginatoprac.data.account.respository

import com.mi.imaginatoprac.data.account.entity.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @POST("login")
    suspend fun login(@Body param: HashMap<String, String>): Response<LoginResponse>
}