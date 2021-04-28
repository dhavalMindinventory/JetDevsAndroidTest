package com.mi.imaginatoprac.data.sharedprefs

import android.content.SharedPreferences

class SharedPrefs(
    private val sharedPreferences: SharedPreferences,
    private val securityGuards: SecurityGuards
) : BaseSharedPreferences(securityGuards) {

    val accessTokenWithPrefix: String?
        get() = accessToken.takeIf { it.isNotEmpty() }?.let {
            StringBuilder().append(PREFIX_ACCESS_TOKEN).append(" ").append(it).toString()
        }

    var accessToken: String
        set(value) = sharedPreferences.put(PREF_SESSION_ACCESS_TOKEN, value)
        get() = sharedPreferences.get(PREF_SESSION_ACCESS_TOKEN, String::class.java)

    var userId: String
        set(value) = sharedPreferences.put(USER_ID, value)
        get() = sharedPreferences.get(USER_ID, String::class.java)

    var email: String
        set(value) = sharedPreferences.put(EMAIL, value)
        get() = sharedPreferences.get(EMAIL, String::class.java)

    var uuid: String
        set(value) = sharedPreferences.put(UUID, value)
        get() = sharedPreferences.get(UUID, String::class.java)

    var name: String
        set(value) = sharedPreferences.put(NAME, value)
        get() = sharedPreferences.get(NAME, String::class.java)

    fun isLoggedIn(): Boolean {
        return userId.isNotEmpty()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun onErrorCallback(callback: SecurityGuards.ErrorCallback) {
        securityGuards.onErrorCallback(callback)
    }

    companion object {
        internal const val PREFS_NAME = "AppPreferences"

        private const val PREFIX = "AppPreferences_"
        private const val PREF_SESSION_ACCESS_TOKEN = PREFIX + "access_token"
        private const val PREF_REFRESH_TOKEN = "refreshToken"
        private const val SESSION_INVALID = "sessionInvalid"
        private const val IS_DEVICE_TOKEN_SENT = "isDeviceTokenSent"
        private const val PREFIX_ACCESS_TOKEN = "Bearer"
        private const val USER_ID = "id"
        private const val EMAIL = "email"
        private const val NAME = "name"
        private const val PHONE_MOBILE = "phoneMobile"
        private const val UUID = "uuid"
        private const val PREFIX_ERROR = "SharedPref Error : %s"
    }
}
