package com.slowerror.rickandmorty.data.api.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetEpisodeListResponse(
    val info: PageInfo,
    val result: List<GetEpisodeByIdResponse>
)
