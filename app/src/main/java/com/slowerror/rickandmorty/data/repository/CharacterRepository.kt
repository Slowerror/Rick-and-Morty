package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import com.slowerror.rickandmorty.common.Resource
import com.slowerror.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacterById(characterId: Int): Resource<Character>

    fun getCharacterList(): Flow<PagingData<Character>>
}