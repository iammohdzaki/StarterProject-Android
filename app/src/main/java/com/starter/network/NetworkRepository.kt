package com.starter.network

import com.starter.data.model.Version
import com.starter.network.client.ApiService
import com.starter.network.helper.ResponseResolver

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
class NetworkRepository(var apiService: ApiService) {

    suspend fun getVersion(): ResponseResolver<Version> {
        val baseModel = apiService.getVersion()
        return if (baseModel.isSuccessful()) {
            ResponseResolver.success(baseModel.toResponseModel(Version::class.java))
        } else {
            ResponseResolver.failure(Throwable(baseModel.message))
        }
    }

}