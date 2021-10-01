package com.starter.local.preferences

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    }

}