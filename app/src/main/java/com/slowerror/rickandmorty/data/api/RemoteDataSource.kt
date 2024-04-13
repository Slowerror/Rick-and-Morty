package com.slowerror.rickandmorty.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val characterPagingDataSource: CharacterPagingDataSource,
    private val episodePagingDataSource: EpisodePagingDataSource,
    private val remoteService: RemoteService
) {

    suspend fun getCharacterById(characterId: Int) = remoteService.getCharacterById(characterId)

    fun getCharacterList(): Flow<PagingData<GetCharacterByIdResponse>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                prefetchDistance = 40,
                enablePlaceholders = false
            )
        ) { characterPagingDataSource }.flow
    }

    fun getCharacterListByName(userRequest: String): Flow<PagingData<GetCharacterByIdResponse>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                prefetchDistance = 40,
                enablePlaceholders = false
            )
        ) { SearchCharacterPagingDataSource(remoteService, userRequest) }.flow
    }

    suspend fun getMultipleCharacterList(characterList: String) =
        remoteService.getMultipleCharacterList(characterList)

    suspend fun getEpisodeById(episodeId: Int) = remoteService.getEpisodeById(episodeId)

    fun getEpisodeList(): Flow<PagingData<GetEpisodeByIdResponse>> {
        return Pager(PagingConfig(pageSize = 20)) { episodePagingDataSource }.flow
    }

    suspend fun getMultipleEpisodeList(episodeList: String) =
        remoteService.getMultipleEpisodeList(episodeList)
}