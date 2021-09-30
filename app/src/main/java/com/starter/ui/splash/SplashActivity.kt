package com.starter.ui.splash

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.starter.BuildConfig
import com.starter.R
import com.starter.data.model.entity.Version
import com.starter.network.helper.ResponseResolver
import com.starter.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    lateinit var splashViewModel: SplashViewModel
    private var _versionData: Version? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        splashViewModel.getVersion()

        splashViewModel.versionData.observe(this, { versionData ->
            when (versionData) {
                is ResponseResolver.Success -> {
                    Toast.makeText(this, versionData.data.version, Toast.LENGTH_SHORT).show()
                    _versionData = versionData.data
                    splashViewModel.getVersionCode()
                }
                is ResponseResolver.Failure -> showToast(versionData.error)
                else -> showToast("")
            }
        })

        splashViewModel.versionCode.observe(this, { versionCode ->
            if (versionCode.isEmpty()) {
                splashViewModel.saveVersion(_versionData?.version ?: BuildConfig.VERSION_NAME)
            } else {
                showToast(versionCode)
            }
        })

    }
}