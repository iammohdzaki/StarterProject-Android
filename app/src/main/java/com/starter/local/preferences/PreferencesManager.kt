package com.starter.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.starter.utils.extensions.justTry
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    /**
     * Save String Type Values
     */
    suspend fun save(key: Preferences.Key<String>, value: String) {
        preferencesFlow.edit { prefs ->
            prefs[key] = value
        }
    }

    /**
     * Get String Type Values
     */
     fun getString(key: Preferences.Key<String>): Flow<String> {
        val value = preferencesFlow.data.map { preferences ->
            preferences[key] ?: ""
        }
        return value
    }

    /**
     * Get Object Type Values
     */
    suspend fun <T> getObject(key: Preferences.Key<String>, className: Class<T>): T? {
        val value = preferencesFlow.data.map { preferences ->
            preferences[key] ?: ""
        }
        if (value.first().isEmpty()) {
            return null
        } else {
            justTry {
                return Gson().fromJson(value.first(), className)
            }
        }
        return null
    }

    /**
     * Save Int type values
     */
    suspend fun save(key: Preferences.Key<Int>, value: Int) {
        preferencesFlow.edit { prefs ->
            prefs[key] = value
        }
    }

    /**
     * Get Int Type Values
     */
    suspend fun getInt(key: Preferences.Key<Int>): Int {
        val value = preferencesFlow.data.map { prefs ->
            prefs[key] ?: -1
        }
        return value.first()
    }

    /**
     * Save Boolean type values
     */
    suspend fun save(key: Preferences.Key<Boolean>, value: Boolean) {
        preferencesFlow.edit { prefs ->
            prefs[key] = value
        }
    }

    /**
     * Get Boolean Type Values
     */
    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        val value = preferencesFlow.data.map { prefs ->
            prefs[key] ?: false
        }
        return value.first()
    }

    /**
     * Save Float type values
     */
    suspend fun save(key: Preferences.Key<Float>, value: Float) {
        preferencesFlow.edit { prefs ->
            prefs[key] = value
        }
    }

    /**
     * Get Float Type Values
     */
    suspend fun getFloat(key: Preferences.Key<Float>): Float {
        val value = preferencesFlow.data.map { prefs ->
            prefs[key] ?: 0f
        }
        return value.first()
    }

    /**
     * Save Long type values
     */
    suspend fun save(key: Preferences.Key<Long>, value: Long) {
        preferencesFlow.edit { prefs ->
            prefs[key] = value
        }
    }

    /**
     * Get Long Type Values
     */
    suspend fun getLong(key: Preferences.Key<Long>): Long {
        val value = preferencesFlow.data.map { prefs ->
            prefs[key] ?: 0L
        }
        return value.first()
    }

    /**
     * Clear Local Storage
     */
    suspend fun clearLocalStorage() {
        preferencesFlow.edit {
            it.clear()
        }
    }

}