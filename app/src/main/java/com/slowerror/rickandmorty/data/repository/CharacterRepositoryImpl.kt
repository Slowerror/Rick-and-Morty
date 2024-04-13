package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.slowerror.rickandmorty.domain.common.Resource
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import com.slowerror.rickandmorty.data.mappers.toModel
import com.slowerror.rickandmorty.data.safeApiCall
import com.slowerror.rickandmorty.domain.model.Character
import com.slowerror.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacterById(characterId: Int): Resource<Character> =
        safeApiCall {
            remoteDataSource.getCharacterById(characterId).body()?.let {
                val networkEpisodes = getEpisodesFromCharacterResponse(it)

                it.toModel(networkEpisodes)

            } ?: Character()
        }


    override fun getCharacterList(): Flow<PagingData<Character>> =
        remoteDataSource.getCharacterList().map { pagingData ->
            pagingData.map { it.toModel() }
        }

    override fun getCharacterListByName(userRequest: String): Flow<PagingData<Character>> =
        remoteDataSource.getCharacterListByName(userRequest).map { pagingData ->
            pagingData.map { it.toModel() }
        }

    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse> {
        val episodeList = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = remoteDataSource.getMultipleEpisodeList(episodeList)

        return request.body().orEmpty()
    }

}