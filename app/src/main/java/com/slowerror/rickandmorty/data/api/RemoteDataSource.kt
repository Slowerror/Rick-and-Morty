package com.slowerror.rickandmorty.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val characterPagingDataSource: CharacterPagingDataSource,
    private val remoteService: RemoteService
) {

    suspend fun getCharacterById(characterId: Int) = withContext(Dispatchers.IO) {
        remoteService.getCharacterById(characterId)
    }

    fun getCharacterList(): Flow<PagingData<GetCharacterByIdResponse>> {
        return Pager(PagingConfig(pageSize = 20)) { characterPagingDataSource }.flow
    }

}