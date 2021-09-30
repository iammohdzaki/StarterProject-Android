package com.starter.utils

import com.starter.data.model.entity.Validation
import com.starter.utils.extensions.isGenericEmpty
import java.util.regex.Pattern

/**
Created by Mohammad Zaki
on Sep,30 2021
 **/
object Validator {

    private const val USERNAME_LENGTH = 6
    private const val PASSWORD_LENGTH = 8
    private const val PHONE_NUMBER_LENGTH = 10
    private const val OTP_LENGTH = 4

    private const val EMAIL_PATTERN = ("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-+]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")

    /**
     * check user name conditions
     *
     * @param username     username
     * @return [Validation] object
     */
    fun checkUserName(username: String): Validation {
        if (username.isGenericEmpty()) {
            return Validation()
        }
        if (!checkUserNameLength(username)) {
            return Validation()
        }
        return if (!checkUserNameChar(username)) {
            Validation()
        } else Validation(true)
    }

    /**
     * Check userName length boolean.
     *
     * @param username the password
     * @return the boolean
     */
    private fun checkUserNameLength(username: String?): Boolean {
        return !(username == null || username.length < USERNAME_LENGTH)
    }

    /**
     * check user name characters
     *
     * @param userNameStr username
     * @return true or false
     */
    private fun checkUserNameChar(userNameStr: String): Boolean {
        val userName = Pattern.compile("[a-zA-Z0-9]{6,20}")
        return userNameStr.matches(userName.toRegex())
    }


}
