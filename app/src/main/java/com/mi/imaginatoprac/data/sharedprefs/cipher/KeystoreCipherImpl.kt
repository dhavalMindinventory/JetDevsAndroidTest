package com.mi.imaginatoprac.data.sharedprefs.cipher

import android.util.Base64
import com.mi.imaginatoprac.data.sharedprefs.utils.KeystoreWrapper
import java.security.Key
import java.security.KeyPair
import java.security.UnrecoverableKeyException
import java.security.spec.MGF1ParameterSpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.crypto.spec.SecretKeySpec

@Suppress("DEPRECATION")
class KeystoreCipherImpl(private val keystoreWrapper: KeystoreWrapper) : KeystoreCipher {

    private var aesKey: ByteArray? = null
    private var aesVectorSpecs: ByteArray? = null

    private var masterKeyAsymmetric: KeyPair? = null

    init {
        if (!keystoreWrapper.isAndroidKeyStoreAsymmetricKeyExist()) {
            keystoreWrapper.createAndroidKeyStoreAsymmetricKey()
        }
    }

    @Throws(UnrecoverableKeyException::class)
    override fun getMasterKeyAsymmetric() {
        masterKeyAsymmetric = keystoreWrapper.androidKeyStoreAsymmetricKeyPair()
    }

    override fun getNewMasterKeyAsymmetric() {
        keystoreWrapper.createAndroidKeyStoreAsymmetricKey()
        masterKeyAsymmetric = keystoreWrapper.androidKeyStoreAsymmetricKeyPair()
    }

    override fun setAesKey(key: String, vectorSpec: String) {
        aesKey = decryptRSA(key)
        aesVectorSpecs = decryptRSA(vectorSpec)
    }

    override fun getNewAesKey(): Pair<String, String> {
        val mapSymmetric = keystoreWrapper.createDefaultSymmetricKey()
        aesKey = mapSymmetric[KeystoreWrapper.AES_MASTER_KEY]
        aesVectorSpecs = mapSymmetric[KeystoreWrapper.AES_VECTOR_KEY]

        val keyEncrypted = encryptRSA(aesKey) ?: ""
        val ivEncrypted = encryptRSA(aesVectorSpecs) ?: ""

        return Pair(keyEncrypted, ivEncrypted)
    }

    @Throws(IllegalStateException::class)
    override fun decrypt(encrypted: String): String {
        if (encrypted.isEmpty()) return ""

        return try {
            val aesCipher = Cipher.getInstance(AES_TRANSFORMATION)
            val iv = IvParameterSpec(aesVectorSpecs)
            val skeySpec = SecretKeySpec(aesKey, AES_ALGORITHM)

            aesCipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

            val original = aesCipher.doFinal(encrypted.decodeToByteArray())
            String(original, Charsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
    }

    override fun encrypt(value: String): String {
        if (value.isEmpty()) return ""

        return try {
            val aesCipher = Cipher.getInstance(AES_TRANSFORMATION)
            val iv = IvParameterSpec(aesVectorSpecs)
            val skeySpec = SecretKeySpec(aesKey, AES_ALGORITHM)
            aesCipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)

            val encrypted = aesCipher.doFinal(value.toByteArray(Charsets.UTF_8))
            encrypted.encodeToString()
        } catch (e: Exception) {
            ""
        }
    }

    private val sp by lazy {
        OAEPParameterSpec(SHA_256, MGF, MGF1ParameterSpec(SHA_1), PSource.PSpecified.DEFAULT)
    }

    private fun encryptRSA(value: ByteArray?): String? {
        if (value == null || masterKeyAsymmetric?.public == null) {
            return null
        }
        val rsaCipher = Cipher.getInstance(RSA_TRANSFORMATION)
        val publicKey = masterKeyAsymmetric?.public

        val encryptedByteArray = rsaCipher.apply {
            init(Cipher.ENCRYPT_MODE, publicKey, sp)
        }.doFinal(value)

        return encryptedByteArray.encodeToString()
    }

    private fun decryptRSA(value: String): ByteArray? {
        if (masterKeyAsymmetric?.private == null) {
            return null
        }
        val rsaCipher = Cipher.getInstance(RSA_TRANSFORMATION)
        val privateKey = masterKeyAsymmetric?.private

        val byteArray = value.decodeToByteArray()

        return rsaCipher.apply {
            init(Cipher.DECRYPT_MODE, privateKey, sp)
        }.doFinal(byteArray)
    }

    override fun deleteMasterKey() {
        keystoreWrapper.removeAndroidKeyStoreKey()
    }

    /**
     * Wraps(encrypts) a key with another key.
     */
    fun wrapKey(keyToBeWrapped: Key): String {
        val rsaCipher = Cipher.getInstance(RSA_TRANSFORMATION)
        rsaCipher.init(Cipher.WRAP_MODE, masterKeyAsymmetric?.public)
        val decodedData = rsaCipher.wrap(keyToBeWrapped)
        return Base64.encodeToString(decodedData, Base64.DEFAULT)
    }

    /**
     * Unwraps(decrypts) a key with another key. Requires wrapped key algorithm and type.
     */
    fun unWrapKey(wrappedKeyData: String, algorithm: String, wrappedKeyType: Int): Key {
        val rsaCipher = Cipher.getInstance(RSA_TRANSFORMATION)
        val encryptedKeyData = Base64.decode(wrappedKeyData, Base64.DEFAULT)
        rsaCipher.init(Cipher.UNWRAP_MODE, masterKeyAsymmetric?.private)
        return rsaCipher.unwrap(encryptedKeyData, algorithm, wrappedKeyType)
    }

    companion object {
        private const val AES_ALGORITHM = "AES"
        private const val AES_TRANSFORMATION = "AES/CBC/PKCS5Padding"
        private const val SHA_256 = "SHA-256"
        private const val SHA_1 = "SHA-1"
        private const val MGF = "MGF1"
        private const val RSA_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
    }

    private fun String.decodeToByteArray(): ByteArray = Base64.decode(this, Base64.DEFAULT)

    private fun ByteArray.encodeToString(): String = Base64.encodeToString(this, Base64.DEFAULT)
}
