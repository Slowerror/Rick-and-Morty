package com.slowerror.rickandmorty.data.api

import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetCharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("$CHARACTER_ENDPOINT/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<GetCharacterByIdResponse>

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacterList(@Query("page") page: Int): Response<GetCharacterListResponse>
}


