package com.mi.imaginatoprac.data.sharedprefs.cipher

interface KeystoreCipher {
    fun setAesKey(key: String, vectorSpec: String)
    fun getNewAesKey(): Pair<String, String>
    fun decrypt(encrypted: String): String
    fun encrypt(value: String): String
    fun getMasterKeyAsymmetric()
    fun getNewMasterKeyAsymmetric()
    fun deleteMasterKey()
}
