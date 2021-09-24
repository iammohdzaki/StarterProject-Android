package com.starter.data.model

import com.google.gson.annotations.SerializedName

/**
Created by Mohammad Zaki
on Sep,23 2021
 **/
data class Version(
    @SerializedName("version") var version: String = "",
    @SerializedName("isForce") var isForce: Int,
)
