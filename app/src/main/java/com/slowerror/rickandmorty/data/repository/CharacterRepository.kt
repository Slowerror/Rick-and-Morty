package com.slowerror.rickandmorty.data.repository

import com.slowerror.rickandmorty.model.Character

interface CharacterRepository {

    suspend fun getCharacterById(characterId: Int): Character
}