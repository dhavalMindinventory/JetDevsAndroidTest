package com.mi.imaginatoprac.data.sharedprefs

interface SecurityGuards {
    fun getStringDecrypted(encryptedString: String?): String
    fun reset()
    fun getUUID(): String
    fun encryptedValue(value: String): String
    fun setSecureKey(key: String, value: String): Boolean
    fun getSecureKey(key: String): String
    fun onErrorCallback(callback: ErrorCallback)

    interface ErrorCallback {
        fun onEncryptionError(msg: String)
    }
}
