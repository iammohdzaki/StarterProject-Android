package com.starter.network.services

import com.starter.data.model.response.BaseModel
import com.starter.network.util.ApiEndpoint
import retrofit2.Response
import retrofit2.http.GET

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
interface ApiService {

    @GET(ApiEndpoint.GET_VERSION)
    suspend fun getVersion(): Response<BaseModel>

}