package com.starter.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Created by Mohammad Zaki
 * on Sep,30 2021
 */
class ValidatorTest {

    /**
     * Test Cases For User Name
     */
    @Test
    fun `check for empty username`() {
        val result = Validator.checkUserName("")
        assertThat(result.status).isFalse()
    }

    @Test
    fun `check for username length`() {
        val result = Validator.checkUserName("zaki")
        assertThat(result.status).isFalse()
    }

    @Test
    fun `check for username characters`() {
        val result = Validator.checkUserName("zaki@#908")
        assertThat(result.status).isFalse()
    }

    @Test
    fun `check for username`() {
        val result = Validator.checkUserName("iammohdzaki")
        assertThat(result.status).isTrue()
    }


}