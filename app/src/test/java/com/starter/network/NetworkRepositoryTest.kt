package com.starter.network

import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.starter.BuildConfig
import com.starter.data.model.entity.Version
import com.starter.network.helper.ApiParams
import com.starter.network.helper.ResponseResolver
import com.starter.network.services.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
Created by Mohammad Zaki
on Oct,01 2021
 **/
class NetworkRepositoryTest {

    lateinit var networkRepository: NetworkRepository
    lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
        networkRepository = NetworkRepository(apiService)
    }


    @Test
    fun `get version api call`() = runBlocking {
        val params = ApiParams.Builder
            .add("key", "zaki")
            .buildForUnitTest()
        println(params.getKeys().toString())
        val result = networkRepository.getVersion(params.getKeys())
        val version = if (result is ResponseResolver.Success) {
            println(result.data.toString())
            result.data.toResponseModel(Version::class.java)
        } else {
            Version()
        }

        assertThat(version.version).isEqualTo("1.0.0")
    }

}