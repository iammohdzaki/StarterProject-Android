package com.starter.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starter.data.model.Version
import com.starter.network.NetworkRepository
import com.starter.network.helper.ResponseResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
Created by Mohammad Zaki
on Sep,23 2021
 **/

@HiltViewModel
class SplashViewModel @Inject
constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    val versionData: MutableLiveData<ResponseResolver<Version>> = MutableLiveData()

    fun getVersion() {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                versionData.value = networkRepository.getVersion()
            }
        }
    }
}