package com.slowerror.rickandmorty.data

import com.slowerror.rickandmorty.domain.common.Resource

const val STARTED_KEY = 1

inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
    return try {
        Resource.success(apiCall.invoke())
    } catch (e: Exception) {
        Resource.error(e.message.toString())
    }
}

fun getPage(page: String?): Int? {
    if (page == null) return null
    return page.substringAfter("?page=").first().digitToInt()
}