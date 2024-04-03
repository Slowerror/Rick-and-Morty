package com.slowerror.rickandmorty.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCharacterListResponse(
    val info: PageInfo,
    val results: List<GetCharacterByIdResponse>
)
