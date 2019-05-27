package com.example.kotlinfutures.model.common

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback
import android.support.v4.os.CancellationSignal
import com.example.kotlinfutures.view.Callback
import java.security.KeyStore
import java.security.KeyStoreException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class FingerprintHelper(
    private val fingerprintManager: FingerprintManagerCompat,
    private val callback: Callback,
    private var cryptoObject: FingerprintManagerCompat.CryptoObject? = null,
    private var keyStore: KeyStore? = null,
    private var keyGenerator: KeyGenerator? = null,
    private var cancellationSignal: CancellationSignal? = null,
    private var selfCancelled: Boolean = false
) : AuthenticationCallback() {

    init {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
            keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES,
                    ANDROID_KEY_STORE
                )
        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
        }
        createKey(DEFAULT_KEY_NAME)
        val defaultCipher: Cipher = Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7
        )

        if (initCipher(defaultCipher,
                DEFAULT_KEY_NAME
            )) {
            cryptoObject = FingerprintManagerCompat.CryptoObject(defaultCipher)
        }
    }

    fun cryptoInstance(): FingerprintManagerCompat.CryptoObject? {
        return cryptoObject
    }

    fun startListening(cryptoObject: FingerprintManagerCompat.CryptoObject) {
        cancellationSignal = CancellationSignal()
        selfCancelled = false
        fingerprintManager.authenticate(cryptoObject, 0, cancellationSignal, this, null)
    }

    fun stopListening() {
        cancellationSignal?.let {
            selfCancelled = true
            it.cancel()
            cancellationSignal = null
        }
    }

    private fun initCipher(cipher: Cipher, keyName: String): Boolean {
        return try {
            keyStore?.load(null)
            val key = keyStore?.getKey(keyName, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            true
        } catch (io: KeyStoreException) {
            false
        }
    }

    private fun createKey(keyName: String) {
        try {
            keyStore?.load(null)

            val builder = KeyGenParameterSpec.Builder(
                keyName,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyGenerator?.init(builder.build())
            keyGenerator?.generateKey()
        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
        }
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        if (!selfCancelled) {
            callback.onError(errString)
        }
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        callback.onAuthenticated()
    }

    override fun onAuthenticationFailed() {
        callback.onError(ERROR_MESSAGE)
    }

    companion object {
        private val ERROR_MESSAGE = "Wrong finger print, please try again"
        private val DEFAULT_KEY_NAME = "default_key"
        private val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}
