package com.starter.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.starter.R
import com.starter.ui.splash.SplashActivity
import com.starter.utils.Foreground

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/
class NotificationService : FirebaseMessagingService() {
    private val NOTIFICATION_VIBRATION_PATTERN =
        longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

    companion object {
        private var sFCMTokenCallback: DeviceTokenInterface? = null
        private val handlerOs = Handler()
        private val FCM_CALL_TIMEOUT = 20000
        private val TAG = DeviceTokenInterface::class.java.name


        /**
         * Sets callback.
         *
         * @param callback the callback
         */
        @JvmStatic
        fun setCallback(callback: DeviceTokenInterface) {
            sFCMTokenCallback = callback
            startHandler()
            initFCM()
        }

        /**
         * Retry.
         *
         * @param callback the callback
         */
        @JvmStatic
        fun retry(callback: DeviceTokenInterface) {
            setCallback(callback)
        }

        private fun startHandler() {
            handlerOs.postDelayed({
                try {
                    sFCMTokenCallback!!.onFailure()
                    sFCMTokenCallback = null
                } catch (e: Exception) {
                    try {
                        sFCMTokenCallback!!.onFailure()
                        sFCMTokenCallback = null
                    } catch (e1: Exception) {
                        e.printStackTrace()
                    }

                }
            }, FCM_CALL_TIMEOUT.toLong())
        }

        private fun initFCM() {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                sFCMTokenCallback!!.onTokenReceived(token ?: "")
                sFCMTokenCallback = null
                clearHandler()
            })
        }

        /**
         * Clear Handler
         */
        private fun clearHandler() {
            handlerOs.removeCallbacksAndMessages(null)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e(TAG, token)
        if (sFCMTokenCallback != null) {
            sFCMTokenCallback!!.onTokenReceived(token)
            sFCMTokenCallback = null
            clearHandler()
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, getString(R.string.log_fcm_message_id) + remoteMessage.messageId!!)
        Log.d(TAG, getString(R.string.log_fcm_notification_message) + remoteMessage.notification!!)
        Log.d(TAG, getString(R.string.log_fcm_data) + remoteMessage.data)
        //Log.d(TAG, getString(R.string.log_fcm_data_message) + remoteMessage.data[AppConstant.MESSAGE]!!)

        showNotification(remoteMessage.data)
    }

    /**
     * Show notification
     *
     * @param data notification data map
     */
    private fun showNotification(data: Map<String, String>) {
        val DEFAULT_CHANNEL_ID = "default"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var notificationIntent: Intent? = null
        notificationIntent = if (!Foreground[application]!!.isForeground) {
            Intent(applicationContext, SplashActivity::class.java)
        } else {
            Intent()
        }

        val pi = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // The user-visible name of the channel.
            val name = getString(R.string.notification_channel_default)
            // The user-visible description of the channel.
            val description = getString(R.string.notification_channel_description_default)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(DEFAULT_CHANNEL_ID, name, importance)
            // Configure the notification channel.
            mChannel.description = description
            mChannel.enableLights(true)
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = NOTIFICATION_VIBRATION_PATTERN
            notificationManager.createNotificationChannel(mChannel)
        }

        val mNotification = NotificationCompat.Builder(this, DEFAULT_CHANNEL_ID)
            .setStyle(NotificationCompat.BigTextStyle().bigText(data[AppConstant.MESSAGE]))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(data[AppConstant.MESSAGE])
            .setContentIntent(pi)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .setChannelId(DEFAULT_CHANNEL_ID)
            .build()
        notificationManager.notify(0, mNotification)
    }
}

object AppConstant {
    const val NOTIFICATION_RECEIVED = "notification_received"
    const val MESSAGE = "message"
}