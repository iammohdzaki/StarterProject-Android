package com.starter.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.common.ConnectionResult
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.starter.BuildConfig
import kotlin.math.min

/**
Created by Mohammad Zaki
on Sep,25 2021
 **/

private const val TAG = "Utils"

object Utils {

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternetConnection(context: Context?): Boolean {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun logRequestBody(`object`: Any?) {
        try {
            if (BuildConfig.DEBUG) {
                val jsonString = prettyJson(`object`)
                val maxLogSize = 4000
                if (jsonString.length > 4000) {
                    for (i in 0..jsonString.length / maxLogSize) {
                        val start = i * maxLogSize
                        var end = (i + 1) * maxLogSize
                        end = min(end, jsonString.length)
                        Log.d(
                            TAG,
                            (if (i == 0) REQUEST_BODY else "") + jsonString.substring(
                                start,
                                end
                            )
                        )
                    }
                } else Log.d(TAG, REQUEST_BODY + jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun prettyJson(`object`: Any?): String {
        var json = "{}"
        if (BuildConfig.DEBUG && `object` != null) {
            val parser = GsonBuilder().setPrettyPrinting().create()
            try {
                json = parser.toJson(JsonParser().parse(objectToJson(`object`)))
            } catch (e: Exception) {
                try {
                    json = parser.toJson(JsonParser().parse(`object`.toString()))
                } catch (ignore: Exception) {
                }
            }
            if (json == null || json.isEmpty() || json.equals("{}", ignoreCase = true)) json = objectToJson(
                `object`
            )
        }
        return json
    }

    private fun objectToJson(`object`: Any): String {
        return Gson().toJson(`object`).replace("\\", "")
    }

}