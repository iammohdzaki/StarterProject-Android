package com.starter.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starter.data.model.entity.Version
import com.starter.local.preferences.PreferencesManager
import com.starter.network.NetworkRepository
import com.starter.network.helper.ResponseResolver
import com.starter.network.helper.StatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
Created by Mohammad Zaki
on Sep,23 2021
 **/
private const val TAG = "SplashViewModel"

@HiltViewModel
class SplashViewModel @Inject
constructor(
    private val networkRepository: NetworkRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {


    private val _versionData: MutableLiveData<ResponseResolver<Version>> = MutableLiveData()
    val versionData = _versionData

    private val _versionCode: MutableLiveData<String> = MutableLiveData()
    val versionCode = _versionCode


    fun getVersion() {
        viewModelScope.launch {
            when (val response = networkRepository.getVersion()) {
                is ResponseResolver.Success -> {
                    versionData.postValue(
                        ResponseResolver.success(
                            response.data.toResponseModel(
                                Version::class.java
                            )
                        )
                    )

                }
                is ResponseResolver.Failure -> {
                    versionData.postValue(ResponseResolver.failure(response.error))
                }
                else -> {
                    versionData.postValue(ResponseResolver.failure(StatusCode.NONE.defaultMessage))
                }
            }
        }
    }

    fun saveVersion(version: String) {
        viewModelScope.launch {
            preferencesManager.updateVersionCode(version)
            Log.d(TAG, "getVersion: ---> Writing version($version)")
        }
    }

    fun getVersionCode() {
        viewModelScope.launch {
            versionCode.postValue(preferencesManager.versionCode.first())
        }
    }
}