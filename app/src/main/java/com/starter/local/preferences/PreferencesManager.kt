package com.starter.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
Created by Mohammad Zaki
on Sep,27 2021
 **/
@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val preferencesFlow = context.dataStore

    val versionCode = preferencesFlow.data.map { preferences ->
        preferences[PreferencesKeys.VERSION_CODE] ?: ""
    }

    suspend fun updateVersionCode(version: String) {
        preferencesFlow.edit { preferencesFlow ->
            preferencesFlow[PreferencesKeys.VERSION_CODE] = version
        }
    }

    object PreferencesKeys {
        val VERSION_CODE = stringPreferencesKey("version_code")
    }
}