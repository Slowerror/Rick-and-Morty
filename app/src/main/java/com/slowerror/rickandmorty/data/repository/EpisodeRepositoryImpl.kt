package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.slowerror.rickandmorty.domain.common.Resource
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import com.slowerror.rickandmorty.data.mappers.toModel
import com.slowerror.rickandmorty.data.safeApiCall
import com.slowerror.rickandmorty.domain.model.Episode
import com.slowerror.rickandmorty.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : EpisodeRepository {

    override suspend fun getEpisodeById(episodeId: Int): Resource<Episode> =
        safeApiCall {
            remoteDataSource.getEpisodeById(episodeId).body()?.let {
                it.toModel(getCharactersFromEpisodeResponse(it))
            } ?: Episode()
        }


    override fun getEpisodeList(): Flow<PagingData<Episode>> =
        remoteDataSource.getEpisodeList().map { pagingData ->
            pagingData.map { it.toModel() }
        }


    private suspend fun getCharactersFromEpisodeResponse(
        episodeByIdResponse: GetEpisodeByIdResponse
    ): List<GetCharacterByIdResponse> {
        val characterList = episodeByIdResponse.characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = remoteDataSource.getMultipleCharacterList(characterList)
        return request.body().orEmpty()
    }


}