package com.starter.ui.splash

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.razir.progressbutton.*
import com.starter.BuildConfig
import com.starter.R
import com.starter.data.model.entity.Version
import com.starter.databinding.ActivitySplashBinding
import com.starter.network.helper.ResponseResolver
import com.starter.notifications.DeviceTokenInterface
import com.starter.notifications.NotificationService
import com.starter.ui.base.BaseActivity
import com.starter.utils.Utils.hasInternetConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity(), DeviceTokenInterface {

    lateinit var splashViewModel: SplashViewModel
    private var _versionData: Version? = null
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        bindProgressButton(binding.btnCheck)
        binding.btnCheck.attachTextChangeAnimator()

        binding.btnCheck.setOnClickListener {
            if (binding.btnCheck.isProgressActive()) {
                binding.btnCheck.hideProgress("Done")
            } else {
                binding.btnCheck.showProgress {
                    progressColor = Color.WHITE
                    buttonText = "Signing Up.."
                }
            }
        }

        registerForPush()
    }

    /**
     *  Registers Device For Push
     */
    private fun registerForPush() {
        if (!hasInternetConnection(this)) {
            showToast(R.string.no_internet_try_again)
            return
        }
        /*if (!isPlayServiceAvailable(this)) {
              return
          }*/
        // Register for push
        NotificationService.setCallback(this)
    }

    override fun onTokenReceived(token: String) {
        Log.d("DEVICE TOKEN", token)
    }

    override fun onFailure() {
        NotificationService.retry(this)
    }
}