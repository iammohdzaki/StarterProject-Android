package com.starter.network.helper

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
sealed class ResponseResolver<T> {
    data class Progress<T>(var loading: Boolean) : ResponseResolver<T>()
    data class Success<T>(var data: T) : ResponseResolver<T>()
    data class Failure<T>(val e: Throwable) : ResponseResolver<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): ResponseResolver<T> =
            Progress(isLoading)

        fun <T> success(data: T): ResponseResolver<T> =
            Success(data)

        fun <T> failure(e: Throwable): ResponseResolver<T> =
            Failure(e)
    }
}
