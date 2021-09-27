package com.starter.network.helper

import retrofit2.Response

/**
Created by Mohammad Zaki
on Sep,25 2021
 **/
abstract class ApiHelper {

    suspend fun <T> safeCall(apiCall: suspend () -> Response<T>): ResponseResolver<T> {
        try{
            val response = apiCall()
            if(response.isSuccessful){
                val status = StatusCode[response.code()]
                val body = response.body()
                body?.let {
                    when(status){
                        StatusCode.SUCCESS -> {
                            return ResponseResolver.success(it)
                        }
                        StatusCode.SESSION_EXPIRE -> {
                            //Implementation Pending
                        }
                        else -> return error(StatusCode.NONE.defaultMessage)
                    }
                }
                return error(StatusCode.NONE.defaultMessage)
            }else{
                return error(StatusCode.EXECUTION_ERROR.defaultMessage)
            }
        }catch (e : Throwable){
            e.printStackTrace()
            return error(StatusCode.PARSING_ERROR.defaultMessage)
        }
    }

    private fun <T> error(errorMessage: String): ResponseResolver<T> =
        ResponseResolver.failure(errorMessage)
}