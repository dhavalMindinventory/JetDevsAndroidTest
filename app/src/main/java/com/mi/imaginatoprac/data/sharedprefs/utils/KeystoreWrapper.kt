package com.mi.imaginatoprac.data.sharedprefs.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.basestructure.app.BuildConfig
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.SecureRandom
import java.util.Calendar
import java.util.HashMap
import javax.security.auth.x500.X500Principal

class KeystoreWrapper(private val context: Context) {

    companion object {
        private const val SIZE_KEY = 2048
        private const val AMOUNT_DURATION = 30
        private const val KEYSTORE_TYPE = "AndroidKeyStore"
        private const val RSA_ALGORITHM = "RSA"
        private const val AES_BYTE_KEY_SIZE = 16
        const val AES_MASTER_KEY = "AES_MASTER"
        const val AES_VECTOR_KEY = "AES_VECTOR"
    }

    private val versionSecurityGuards: Int = BuildConfig.SECURITY_GUARD_VERSION

    private val keystoreAliasName = "AndroidAlias$versionSecurityGuards"

    private val keyStore: KeyStore = createAndroidKeyStore()

    fun isAndroidKeyStoreAsymmetricKeyExist(): Boolean {
        return keyStore.containsAlias(keystoreAliasName)
    }

    fun removeAndroidKeyStoreKey() = keyStore.deleteEntry(keystoreAliasName)

    /**
     * @return asymmetric keypair from Android Key Store or null if any key with given alias exists
     */
    fun androidKeyStoreAsymmetricKeyPair(): KeyPair? {
        val privateKey = keyStore.getKey(keystoreAliasName, null) as PrivateKey?
        val publicKey = keyStore.getCertificate(keystoreAliasName)?.publicKey

        return if (privateKey != null && publicKey != null) {
            KeyPair(publicKey, privateKey)
        } else {
            null
        }
    }

    fun createDefaultSymmetricKey(): HashMap<String, ByteArray> {
        val key = ByteArray(AES_BYTE_KEY_SIZE)
        val ivSpec = ByteArray(AES_BYTE_KEY_SIZE)
        SecureRandom().apply {
            nextBytes(key)
            nextBytes(ivSpec)
        }

        return hashMapOf(
            AES_MASTER_KEY to key,
            AES_VECTOR_KEY to ivSpec
        )
    }

    /**
     * Creates asymmetric RSA key with default [KeyProperties.BLOCK_MODE_ECB] and
     * [KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1] and saves it to Android Key Store.
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun createAndroidKeyStoreAsymmetricKey(): KeyPair? {
        val generator = KeyPairGenerator.getInstance(RSA_ALGORITHM, KEYSTORE_TYPE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initGeneratorWithKeyGenParameterSpec(generator)
        } else {
            initGeneratorWithKeyPairGeneratorSpec(generator)
        }

        return try {
            generator.generateKeyPair()
        } catch (e: IllegalStateException) {
            null
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun initGeneratorWithKeyGenParameterSpec(generator: KeyPairGenerator) {
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            keystoreAliasName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
            setDigests(KeyProperties.DIGEST_SHA256)
            setKeySize(SIZE_KEY)
            build()
        }
        generator.initialize(keyGenParameterSpec)
    }

    private fun initGeneratorWithKeyPairGeneratorSpec(generator: KeyPairGenerator) {
        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, AMOUNT_DURATION)
        val keyGenParameterSpec = KeyPairGeneratorSpec.Builder(context).run {
            setAlias(keystoreAliasName)
            setSubject(X500Principal("CN=$keystoreAliasName"))
            setSerialNumber(BigInteger.TEN)
            setStartDate(start.time)
            setEndDate(end.time)
            build()
        }
        generator.initialize(keyGenParameterSpec)
    }

    private fun createAndroidKeyStore(): KeyStore {
        val keyStore = KeyStore.getInstance(KEYSTORE_TYPE)
        keyStore.load(null)
        return keyStore
    }
}
