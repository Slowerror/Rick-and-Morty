package com.slowerror.rickandmorty.data.api

import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val api: Api) {

    suspend fun getCharacterById(characterId: Int) = withContext(Dispatchers.IO) {
        api.getCharacterById(characterId).body() ?: GetCharacterByIdResponse()
    }

}