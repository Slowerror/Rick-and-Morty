package com.slowerror.rickandmorty.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCharacterByIdResponse(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: Origin = Origin(),
    val location: Location = Location(),
    val image: String = "",
    val episode: List<String> = emptyList(),
    val url: String = "",
    val created: String = ""
) {
    @JsonClass(generateAdapter = true)
    data class Location(
        val name: String = "",
        val url: String = ""
    )

    @JsonClass(generateAdapter = true)
    data class Origin(
        val name: String = "",
        val url: String = ""
    )
}