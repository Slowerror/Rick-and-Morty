package com.slowerror.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.slowerror.rickandmorty.domain.common.Resource
import com.slowerror.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacterById(characterId: Int): Resource<Character>

    fun getCharacterList(): Flow<PagingData<Character>>

    fun getCharacterListByName(userRequest: String): Flow<PagingData<Character>>
}