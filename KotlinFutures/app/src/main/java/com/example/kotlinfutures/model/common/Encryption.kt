package com.example.kotlinfutures.model.common

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Encryption {
    companion object {
        private const val keyLength = 256
        private const val vectorLength = 16
        private const val iterationCount = 1324
        private const val secretKeyAlgorithm = "PBKDF2WithHmacSHA1"
        private const val cipherTransformation = "AES/CBC/PKCS5Padding"
        private const val specKeyAlgorithm = "AES"

        fun encrypt(password: String, login: String): HashMap<String, ByteArray> {
            val map = HashMap<String, ByteArray>()
            val random = SecureRandom()
            val salt = ByteArray(keyLength)
            random.nextBytes(salt)
            val pbKeySpec = PBEKeySpec(login.toCharArray(), salt, iterationCount, keyLength)
            val secretKeyFactory = SecretKeyFactory.getInstance(secretKeyAlgorithm)
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, specKeyAlgorithm)

            val ivRandom = SecureRandom()
            val initVector = ByteArray(vectorLength)
            ivRandom.nextBytes(initVector)
            val ivParamSpec = IvParameterSpec(initVector)

            val cipher = Cipher.getInstance(cipherTransformation)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec)
            val encrypted = cipher.doFinal(password.toByteArray())
            map["salt"] = salt
            map["iv"] = initVector
            map["encrypted"] = encrypted
            return map
        }

        fun decrypt(map: Map<String, ByteArray>, login: String): String {
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            val pbKeySpec = PBEKeySpec(login.toCharArray(), salt, iterationCount, keyLength)
            val secretKeyFactory = SecretKeyFactory.getInstance(secretKeyAlgorithm)
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, specKeyAlgorithm)

            val cipher = Cipher.getInstance(cipherTransformation)
            val ivParamSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec)
            return String(cipher.doFinal(encrypted))
        }
    }
}
