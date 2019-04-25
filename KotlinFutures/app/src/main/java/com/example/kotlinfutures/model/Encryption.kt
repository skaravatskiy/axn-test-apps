package com.example.kotlinfutures.model

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Encryption {
    companion object {

        fun encrypt(password: String, login: String): HashMap<String, ByteArray> {
            val map = HashMap<String, ByteArray>()
            val random = SecureRandom()
            val salt = ByteArray(256)
            random.nextBytes(salt)
            val pbKeySpec = PBEKeySpec(login.toCharArray(), salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val ivRandom = SecureRandom()
            val initVector = ByteArray(16)
            ivRandom.nextBytes(initVector)
            val ivParamSpec = IvParameterSpec(initVector)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec)
            val encrypted = cipher.doFinal(password.toByteArray()) // 2
            map["salt"] = salt
            map["iv"] = initVector
            map["encrypted"] = encrypted
            return map
        }

        fun decrypt(map: Map<String, ByteArray>, login: String): String {
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            val pbKeySpec = PBEKeySpec(login.toCharArray(), salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val ivParamSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec)
            val res = String(cipher.doFinal(encrypted))
            return res
        }
    }
}