package com.slowerror.rickandmorty.data

import com.slowerror.rickandmorty.common.Resource

const val STARTED_KEY = 1

inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
    return try {
        Resource.success(apiCall.invoke())
    } catch (e: Exception) {
        Resource.error(e.message.toString())
    }
}