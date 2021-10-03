package com.starter.network

import com.starter.data.model.response.BaseModel
import com.starter.network.helper.ApiHelper
import com.starter.network.helper.ApiParams
import com.starter.network.services.ApiService
import com.starter.network.helper.ResponseResolver

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
class NetworkRepository(var apiService: ApiService) : ApiHelper() {

    suspend fun getVersion(params: HashMap<String, Any>): ResponseResolver<BaseModel> {
        return safeCall { apiService.getVersion(params) }
    }

}