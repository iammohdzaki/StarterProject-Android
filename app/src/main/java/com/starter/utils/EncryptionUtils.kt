package com.starter.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/
object EncryptionUtils {

    private val initVector: String = "Qwe2@\$%^#\$%werty"
    private val secretKey: String = "eShVmYq3t6v9y\$B&E)H@McQfTjWnZr4u"


    fun encrypt(value: String): String {
        val iv = IvParameterSpec(initVector.toByteArray())
        val sKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv)

        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }


    fun decrypt(encrypted: String): String {
        val iv = IvParameterSpec(initVector.toByteArray())
        val sKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv)

        val value = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT))
        return String(value)
    }


}