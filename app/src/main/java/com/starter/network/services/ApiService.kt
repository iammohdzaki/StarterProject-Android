package com.starter.network.services

import com.starter.data.model.response.BaseModel
import com.starter.network.helper.ApiParams
import com.starter.network.util.ApiEndpoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
interface ApiService {

    @POST(ApiEndpoint.GET_VERSION)
    suspend fun getVersion(@HeaderMap params: HashMap<String, Any>): Response<BaseModel>

}