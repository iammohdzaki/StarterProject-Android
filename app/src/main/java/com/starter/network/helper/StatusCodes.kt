package com.starter.network.helper

/**
Created by Mohammad Zaki
on Sep,23 2021
 **/
enum class StatusCode(val code: Int) {
    NONE(0),
    SUCCESS(200),
    FAILURE(201),
    SESSION_EXPIRE(101),
    REQUEST_ERROR(400),
    EXECUTION_ERROR(404),
    NETWORK_ERROR(411),
    PARSING_ERROR(413);

    companion object {
        operator fun get(statusCode: Int): StatusCode {
            var status = NONE
            for (value in values()) {
                if (value.code == statusCode) {
                    status = value
                    break
                }
            }
            return status
        }
    }
}