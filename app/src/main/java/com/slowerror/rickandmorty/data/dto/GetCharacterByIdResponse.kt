package com.slowerror.rickandmorty.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCharacterByIdResponse(
    val created: String = "",
    val episode: List<String> = emptyList(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val location: Location = Location(),
    val name: String = "",
    val origin: Origin = Origin(),
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
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