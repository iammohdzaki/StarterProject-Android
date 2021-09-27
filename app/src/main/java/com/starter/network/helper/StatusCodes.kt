package com.starter.network.helper

/**
Created by Mohammad Zaki
on Sep,23 2021
 **/
enum class StatusCode(val code: Int,val defaultMessage : String) {
    NONE(0,"Something went wrong!"),
    SUCCESS(200,""),
    FAILURE(201,""),
    SESSION_EXPIRE(101,""),
    REQUEST_ERROR(400,""),
    EXECUTION_ERROR(404,"Remote server failed to respond"),
    NETWORK_ERROR(411,""),
    PARSING_ERROR(413,"An error occurred while parsing");

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