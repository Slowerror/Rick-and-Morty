package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import com.slowerror.rickandmorty.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacterById(characterId: Int): Character

    fun getCharacterList(): Flow<PagingData<Character>>
}