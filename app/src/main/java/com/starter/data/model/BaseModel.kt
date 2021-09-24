package com.starter.data.model

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.starter.network.helper.StatusCode
import com.starter.ui.dialogs.AlertDialog
import dagger.hilt.android.qualifiers.ApplicationContext

/**
Created by Mohammad Zaki
on Sep,21 2021
 **/
data class BaseModel(
    @SerializedName("data") var data: Any? = null,
    @SerializedName("status") var status: Int,
    @SerializedName("message") var message: String = ""
) {

    fun <T> toResponseModel(classRef: Class<T>): T {
        return Gson().fromJson(Gson().toJson(data), classRef)
    }

    fun isSuccessful(): Boolean {
        when (StatusCode[status]) {
            StatusCode.SUCCESS -> {
                return true
            }
            StatusCode.FAILURE -> {
                return false
            }
            StatusCode.SESSION_EXPIRE -> {
                return false
            }
            else -> return false
        }
    }


}
