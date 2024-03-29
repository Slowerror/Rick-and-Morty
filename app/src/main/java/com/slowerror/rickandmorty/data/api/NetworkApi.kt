package com.slowerror.rickandmorty.data.api

import com.slowerror.rickandmorty.data.dto.Character
import retrofit2.http.GET

interface NetworkApi {

    @GET("$CHARACTER_ENDPOINT/2")
    suspend fun getCharacters(): Character
}


