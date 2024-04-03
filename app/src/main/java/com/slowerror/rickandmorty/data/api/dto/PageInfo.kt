package com.slowerror.rickandmorty.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
