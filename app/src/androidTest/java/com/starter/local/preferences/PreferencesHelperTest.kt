package com.starter.local.preferences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import com.starter.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


/**
 * Created by Mohammad Zaki
 * on Sep,30 2021
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
class PreferencesHelperTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var preferencesManager: PreferencesManager

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun saveString() = runBlockingTest {
        val keyValue = Pair("key1", "testing")
        preferencesManager.save(stringPreferencesKey(keyValue.first), keyValue.second)

        val result = preferencesManager.getString(stringPreferencesKey(keyValue.first)).asLiveData().getOrAwaitValue()
        assertThat(result).contains(keyValue.second)
    }

}