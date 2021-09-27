package com.starter.data.model.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

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

}
