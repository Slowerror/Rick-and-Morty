package com.slowerror.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.slowerror.rickandmorty.data.api.CharacterPagingSource
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.mappers.toModel
import com.slowerror.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val characterPagingSource: CharacterPagingSource
) : CharacterRepository {

    override suspend fun getCharacterById(characterId: Int): Character {
        return remoteDataSource.getCharacterById(characterId).toModel()
    }

    override fun getCharacterList(): Flow<PagingData<Character>> {
        return Pager(PagingConfig(pageSize = 5)) { characterPagingSource }.flow
            .map { pagingData ->
                pagingData.map { it.toModel() }
            }
    }

}