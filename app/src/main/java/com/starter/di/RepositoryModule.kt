package com.starter.di

import com.starter.network.client.ApiService
import com.starter.network.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): NetworkRepository {
        return NetworkRepository(apiService)
    }

}