package com.starter.network.helper

import com.starter.utils.Utils

/**
Created by Mohammad Zaki
on Oct,03 2021
 **/
class ApiParams(builder: Builder, var showLog: Boolean = true) {
    private var hashMap = HashMap<String, Any>()

    init {
        this.hashMap = builder.hashMap
    }

    fun getKeys(): HashMap<String, Any> {
        if (showLog) Utils.logRequestBody(hashMap)
        return hashMap
    }

    companion object Builder {
        private val hashMap = HashMap<String, Any>()

        fun add(key: String, value: Any?): Builder {
            if (value == null) return this
            hashMap[key] = value
            return this
        }

        fun build(): ApiParams {
            return ApiParams(this)
        }

        fun buildForUnitTest(): ApiParams {
            return ApiParams(this, false)
        }

    }
}