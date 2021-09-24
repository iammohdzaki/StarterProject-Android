package com.starter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}