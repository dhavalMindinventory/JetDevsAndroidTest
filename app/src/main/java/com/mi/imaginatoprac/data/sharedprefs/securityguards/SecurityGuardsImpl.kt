package com.mi.imaginatoprac.data.sharedprefs.securityguards

import android.content.Context
import android.content.SharedPreferences
import com.basestructure.app.BuildConfig
import com.mi.imaginatoprac.data.sharedprefs.SecurityGuards
import com.mi.imaginatoprac.data.sharedprefs.cipher.KeystoreCipher
import com.mi.imaginatoprac.data.sharedprefs.cipher.KeystoreCipherImpl
import com.mi.imaginatoprac.data.sharedprefs.utils.KeystoreWrapper
import timber.log.Timber
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import java.util.UUID
import javax.crypto.AEADBadTagException
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.inject.Inject

class SecurityGuardsImpl @Inject constructor(
    private val cipherSharedPrefs: SharedPreferences,
    private val cipher: KeystoreCipher
) : SecurityGuards {

    companion object {
        private const val SECURED_PREFS_NAME = "SecurityGuardConfig"
        private const val SECURED_PREFIX = "cipher_"
        private const val PREF_AES_KEY = SECURED_PREFIX + "securedKey01"
        private const val PREF_IV_SPEC = SECURED_PREFIX + "securedKey02"
        private const val PREF_UID = SECURED_PREFIX + "Uid"
    }

    class Factory {
        fun create(context: Context): SecurityGuards {
            val versionSecurityGuards = BuildConfig.SECURITY_GUARD_VERSION
            val keystoreWrapper = KeystoreWrapper(context)
            val cipher = KeystoreCipherImpl(keystoreWrapper)
            val sharedPrefs = context.getSharedPreferences(
                StringBuilder().append(
                    SECURED_PREFS_NAME
                ).append(
                    versionSecurityGuards
                ).toString(),
                Context.MODE_PRIVATE
            )
            return SecurityGuardsImpl(sharedPrefs, cipher)
        }
    }

    init {
        try {
            cipher.getMasterKeyAsymmetric()
        } catch (exception: UnrecoverableKeyException) {
            reset()
        }
        try {
            if (aesKey.isEmpty() || ivSpec.isEmpty()) {
                createNewAesKey()
            } else {
                cipher.setAesKey(aesKey, ivSpec)
            }

            if (getUUID().isEmpty()) {
                val uuid = UUID.randomUUID().toString()
                setSecureKey(PREF_UID, uuid)
            }
        } catch (ex: IllegalStateException) {
            /*
            * @exception IllegalStateException if this cipher is in a wrong state
            * (e.g., has not been initialized)
            * */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: IllegalArgumentException) {
            /*
             * @exception IllegalArgumentException if <code>algorithm</code>
             * is null or <code>key</code> is null or empty.
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: NoSuchAlgorithmException) {
            /*
             * @exception NoSuchAlgorithmException if <code>transformation</code>
             * is null, empty, in an invalid format,
             * or if no Provider supports a CipherSpi implementation for the
             * specified algorithm.
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: NoSuchPaddingException) {
            /*
             * @exception NoSuchPaddingException if <code>transformation</code>
             *          contains a padding scheme that is not available.
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: InvalidKeyException) {
            /*
             * @exception InvalidKeyException if the given key is inappropriate for
             * initializing this cipher, or its keysize exceeds the maximum allowable
             * keysize (as determined from the configured jurisdiction policy files).
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: InvalidAlgorithmParameterException) {
            /*
             * @exception InvalidAlgorithmParameterException if the given algorithm
             * parameters are inappropriate for this cipher,
             * or this cipher requires
             * algorithm parameters and <code>params</code> is null, or the given
             * algorithm parameters imply a cryptographic strength that would exceed
             * the legal limits (as determined from the configured jurisdiction
             * policy files).
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: UnsupportedOperationException) {
            /*
             * @throws UnsupportedOperationException if (@code opmode} is
             * {@code WRAP_MODE} or {@code UNWRAP_MODE} but the mode is not implemented
             * by the underlying {@code CipherSpi}.
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: IllegalBlockSizeException) {
            /*
             * @exception IllegalBlockSizeException if this cipher is a block cipher,
             * no padding has been requested (only in encryption mode), and the total
             * input length of the data processed by this cipher is not a multiple of
             * block size; or if this encryption algorithm is unable to
             * process the input data provided.
             */
            Timber.e("initial error : $ex")
            reset()
        } catch (ex: BadPaddingException) {
            /*
             * @exception BadPaddingException if this cipher is in decryption mode,
             * and (un)padding has been requested, but the decrypted data is not
             * bounded by the appropriate padding bytes
             */
            Timber.e("initial error : ${ex.localizedMessage}")
            reset()
        } catch (ex: AEADBadTagException) {
            /*
             * @exception AEADBadTagException if this cipher is decrypting in an
             * AEAD mode (such as GCM/CCM), and the received authentication tag
             * does not match the calculated value
             */
            Timber.e("initial error : ${ex.localizedMessage}")
            reset()
        }
    }

    private var errorCallback: SecurityGuards.ErrorCallback? = null

    private var aesKey: String
        set(value) = cipherSharedPrefs.put(PREF_AES_KEY, value)
        get() = cipherSharedPrefs.get(PREF_AES_KEY, String::class.java)

    private var ivSpec: String
        set(value) = cipherSharedPrefs.put(PREF_IV_SPEC, value)
        get() = cipherSharedPrefs.get(PREF_IV_SPEC, String::class.java)

    private fun createNewAesKey() {
        val keyPair = cipher.getNewAesKey()
        aesKey = keyPair.first
        ivSpec = keyPair.second
    }

    override fun onErrorCallback(callback: SecurityGuards.ErrorCallback) {
        this.errorCallback = callback
    }

    override fun getStringDecrypted(encryptedString: String?): String {
        return try {
            encryptedString?.let { cipher.decrypt(it) } ?: ""
        } catch (ex: IllegalStateException) {
            /*
            * @exception IllegalStateException if this cipher is in a wrong state
            * (e.g., has not been initialized)
            * */
            errorHandler(ex.localizedMessage)
            ex.printStackTrace()
            reset()
            ""
        } catch (ex: IllegalArgumentException) {
            /*
             * @exception IllegalArgumentException if <code>algorithm</code>
             * is null or <code>key</code> is null or empty.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: NoSuchAlgorithmException) {
            /*
             * @exception NoSuchAlgorithmException if <code>transformation</code>
             *          is null, empty, in an invalid format,
             *          or if no Provider supports a CipherSpi implementation for the
             *          specified algorithm.
             *
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: NoSuchPaddingException) {
            /*
             * @exception NoSuchPaddingException if <code>transformation</code>
             *          contains a padding scheme that is not available.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: InvalidKeyException) {
            /*
             * @exception InvalidKeyException if the given key is inappropriate for
             * initializing this cipher, or its keysize exceeds the maximum allowable
             * keysize (as determined from the configured jurisdiction policy files).
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: InvalidAlgorithmParameterException) {
            /*
             * @exception InvalidAlgorithmParameterException if the given algorithm
             * parameters are inappropriate for this cipher,
             * or this cipher requires
             * algorithm parameters and <code>params</code> is null, or the given
             * algorithm parameters imply a cryptographic strength that would exceed
             * the legal limits (as determined from the configured jurisdiction
             * policy files).
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: UnsupportedOperationException) {
            /*
             * @throws UnsupportedOperationException if (@code opmode} is
             * {@code WRAP_MODE} or {@code UNWRAP_MODE} but the mode is not implemented
             * by the underlying {@code CipherSpi}.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: IllegalBlockSizeException) {
            /*
             * @exception IllegalBlockSizeException if this cipher is a block cipher,
             * no padding has been requested (only in encryption mode), and the total
             * input length of the data processed by this cipher is not a multiple of
             * block size; or if this encryption algorithm is unable to
             * process the input data provided.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: BadPaddingException) {
            /*
             * @exception BadPaddingException if this cipher is in decryption mode,
             * and (un)padding has been requested, but the decrypted data is not
             * bounded by the appropriate padding bytes
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: AEADBadTagException) {
            /*
             * @exception AEADBadTagException if this cipher is decrypting in an
             * AEAD mode (such as GCM/CCM), and the received authentication tag
             * does not match the calculated value
             */
            errorHandler(ex.localizedMessage)
            ""
        }
    }

    override fun encryptedValue(value: String): String {
        return try {
            return cipher.encrypt(value)
        } catch (ex: IllegalStateException) {
            /*
            * @exception IllegalStateException if this cipher is in a wrong state
            * (e.g., has not been initialized)
            * */
            errorHandler(ex.localizedMessage)
            ex.printStackTrace()
            reset()
            ""
        } catch (ex: IllegalArgumentException) {
            /*
             * @exception IllegalArgumentException if <code>algorithm</code>
             * is null or <code>key</code> is null or empty.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: NoSuchAlgorithmException) {
            /*
             * @exception NoSuchAlgorithmException if <code>transformation</code>
             *          is null, empty, in an invalid format,
             *          or if no Provider supports a CipherSpi implementation for the
             *          specified algorithm.
             *
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: NoSuchPaddingException) {
            /*
             * @exception NoSuchPaddingException if <code>transformation</code>
             *          contains a padding scheme that is not available.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: InvalidKeyException) {
            /*
             * @exception InvalidKeyException if the given key is inappropriate for
             * initializing this cipher, or its keysize exceeds the maximum allowable
             * keysize (as determined from the configured jurisdiction policy files).
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: InvalidAlgorithmParameterException) {
            /*
             * @exception InvalidAlgorithmParameterException if the given algorithm
             * parameters are inappropriate for this cipher,
             * or this cipher requires
             * algorithm parameters and <code>params</code> is null, or the given
             * algorithm parameters imply a cryptographic strength that would exceed
             * the legal limits (as determined from the configured jurisdiction
             * policy files).
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: UnsupportedOperationException) {
            /*
             * @throws UnsupportedOperationException if (@code opmode} is
             * {@code WRAP_MODE} or {@code UNWRAP_MODE} but the mode is not implemented
             * by the underlying {@code CipherSpi}.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: IllegalBlockSizeException) {
            /*
             * @exception IllegalBlockSizeException if this cipher is a block cipher,
             * no padding has been requested (only in encryption mode), and the total
             * input length of the data processed by this cipher is not a multiple of
             * block size; or if this encryption algorithm is unable to
             * process the input data provided.
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: BadPaddingException) {
            /*
             * @exception BadPaddingException if this cipher is in decryption mode,
             * and (un)padding has been requested, but the decrypted data is not
             * bounded by the appropriate padding bytes
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: AEADBadTagException) {
            /*
             * @exception AEADBadTagException if this cipher is decrypting in an
             * AEAD mode (such as GCM/CCM), and the received authentication tag
             * does not match the calculated value
             */
            errorHandler(ex.localizedMessage)
            ""
        } catch (ex: Exception) {

            ""
        }
    }

    override fun reset() {
        Timber.e("securityGuards >> reset")
        cipherSharedPrefs.edit().clear().apply()
        cipher.deleteMasterKey()
        cipher.getNewMasterKeyAsymmetric()
        createNewAesKey()
    }

    override fun getUUID(): String {
        return getSecureKey(PREF_UID)
    }

    override fun setSecureKey(key: String, value: String): Boolean {
        cipherSharedPrefs.put(key, cipher.encrypt(value))
        return cipherSharedPrefs.get(key, String::class.java).isNotEmpty()
    }

    override fun getSecureKey(key: String): String {
        val encryptedString = cipherSharedPrefs.get(key, String::class.java)
        return cipher.decrypt(encryptedString)
    }

    private fun errorHandler(errorMsg: String) {
        reset()
        errorCallback?.onEncryptionError(errorMsg)
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun <T> SharedPreferences.get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> getString(key, "")
            Boolean::class.java -> getBoolean(key, false)
            Float::class.java -> getFloat(key, -1f)
            Double::class.java -> getFloat(key, -1f)
            Int::class.java -> getInt(key, -1)
            Long::class.java -> getLong(key, -1L)
            else -> null
        } as T

    private fun <T> SharedPreferences.put(key: String, data: T) {
        val editor = edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }
}
