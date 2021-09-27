package com.starter.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.starter.R
import com.starter.network.helper.ResponseResolver
import com.starter.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        splashViewModel.getVersion()

        splashViewModel.versionData.observe(this, { versionData ->
            when (versionData) {
                is ResponseResolver.Success -> Toast.makeText(this, versionData.data.version, Toast.LENGTH_SHORT).show()
                is ResponseResolver.Failure -> this@SplashActivity.toast(versionData.error)
                else -> this@SplashActivity.toast("")
            }
        })
    }
}