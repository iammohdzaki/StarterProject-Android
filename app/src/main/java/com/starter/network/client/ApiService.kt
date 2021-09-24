package com.starter.network.client

import com.starter.data.model.BaseModel
import retrofit2.http.GET

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
interface ApiService {

    @GET(ApiEndpoint.GET_VERSION)
    suspend fun getVersion(): BaseModel

}