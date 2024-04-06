package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.slowerror.rickandmorty.common.Resource
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.mappers.toModel
import com.slowerror.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacterById(characterId: Int): Resource<Character> =
        safeApiCall {
            remoteDataSource.getCharacterById(characterId).body()?.toModel() ?: Character()
        }


    override fun getCharacterList(): Flow<PagingData<Character>> {
        return remoteDataSource.getCharacterList().map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            Resource.success(apiCall.invoke())
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }
}