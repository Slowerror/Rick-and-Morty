package com.slowerror.rickandmorty.data.api

import com.slowerror.rickandmorty.data.dto.GetCharacterByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("$CHARACTER_ENDPOINT/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<GetCharacterByIdResponse>

    @GET("$CHARACTER_ENDPOINT/?page={page}")
    suspend fun getCharacterList(@Path("page") page: Int): List<GetCharacterByIdResponse>
}


