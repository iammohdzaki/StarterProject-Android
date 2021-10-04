package com.starter.notifications

/**
Created by Mohammad Zaki
on Oct,04 2021
 **/
interface DeviceTokenInterface {
    /**
     * On token received.
     * @param token the token
     */
    fun onTokenReceived(token: String)

    /**
     * On failure.
     */
    fun onFailure()
}