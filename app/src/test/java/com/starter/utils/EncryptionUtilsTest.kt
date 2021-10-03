package com.starter.utils

import android.util.Base64
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.slot
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * Created by Mohammad Zaki
 * on Oct,03 2021
 */
@RunWith(JUnit4::class)
class EncryptionUtilsTest {

    @Before
    fun `Bypass android_util_Base64 to java_util_Base64`() {
        mockkStatic(Base64::class)
        val arraySlot = slot<ByteArray>()

        every {
            Base64.encodeToString(capture(arraySlot), Base64.DEFAULT)
        } answers {
            java.util.Base64.getEncoder().encodeToString(arraySlot.captured)
        }

        val stringSlot = slot<String>()
        every {
            Base64.decode(capture(stringSlot), Base64.DEFAULT)
        } answers {
            java.util.Base64.getDecoder().decode(stringSlot.captured)
        }
    }

    @Test
    fun checkEncryption() {
        val valueToTest = "Mohammad Zaki"
        val expectedResult = "FOymFHlZFp53Hf0aTteAEw=="
        println("Value - $valueToTest")
        val result = EncryptionUtils.encrypt(valueToTest)
        println("Encrypted - $result")
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun checkDecryption() {
        val valueToTest = "FOymFHlZFp53Hf0aTteAEw=="
        val expectedResult = "Mohammad Zaki"
        println("Value - $valueToTest")
        val result = EncryptionUtils.decrypt(valueToTest)
        println("Decrypted - $result")
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun checkEncryptionDecryption() {
        val json = "{\"user_name\":\"zakim\",\"password\":\"Qwerty@123\"}"
        val expectedResult = "BHBaHH5ECTq4O2zlZz4U9v9rMQkeh6xG46OsP4yXJtOwIpEHVZTgA9lo+iBJenEi"
        println("JSON -> $json")
        val encryptedValue = EncryptionUtils.encrypt(json)
        println("Encrypted Value -> $encryptedValue")
        val decryptedValue = EncryptionUtils.decrypt(encryptedValue)
        println("Decrypted Value -> $decryptedValue")
        assertThat(decryptedValue).contains(json)
    }

}