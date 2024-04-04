package com.slowerror.rickandmorty.data.api

import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetCharacterListResponse
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    @GET("$CHARACTER_ENDPOINT/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<GetCharacterByIdResponse>

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacterList(@Query("page") page: Int): Response<GetCharacterListResponse>


    companion object {
        private const val BASE_URL="https://rickandmortyapi.com/api/"
        private const val CHARACTER_ENDPOINT = "character"
        private const val LOCATION_ENDPOINT = "location"
        private const val EPISODE_ENDPOINT = "episode"

        private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


        private val moshi = Moshi.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


        fun runService(): RemoteService = retrofit.create(RemoteService::class.java)
    }

}


