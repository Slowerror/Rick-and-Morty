package com.slowerror.rickandmorty.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    @Json(name = "gender")
    val gender: String
)
