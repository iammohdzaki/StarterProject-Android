package com.starter.local.preferences

import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

/**
Created by Mohammad Zaki
on Sep,29 2021
 **/
@Singleton
class PreferencesHelper @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    object PreferencesKeys {
        val VERSION_CODE = stringPreferencesKey("version_code")
        val VERSION_OBJ = stringPreferencesKey("version_data")
    }

    suspend fun updateVersion(value: String) {
        preferencesManager.save(PreferencesKeys.VERSION_CODE, value)
    }

    suspend fun getVersion(): String {
        return ""
    }

}