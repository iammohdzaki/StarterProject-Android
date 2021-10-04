package com.starter

import android.app.Application
import com.starter.utils.Foreground
import dagger.hilt.android.HiltAndroidApp

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //Initialize App State
        Foreground.init(this)
    }
}