package com.mi.imaginatoprac.data.account.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import kotlinx.serialization.Transient

@Keep
@Serializable
data class LoginResponse(
    @SerialName("data") var `data`: Data? = null,
    @SerialName("error_message") var errorMessage: String? = null,
    @SerialName("result") var result: Int?
) {
    @Keep
    @Serializable
    data class Data(

        @SerialName("user") var user: User? = null
    ) {
        @Keep
        @Serializable
        data class User(
            @SerialName("created_at") var createdAt: String? = "",
            @SerialName("userId") var userId: Int? = 0,
            @SerialName("userName") var userName: String? = ""
        )
    }
}