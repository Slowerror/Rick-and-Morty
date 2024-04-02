package com.slowerror.rickandmorty.data.repository

import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.mappers.toModel
import com.slowerror.rickandmorty.model.Character
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacterById(characterId: Int): Character {
        return remoteDataSource.getCharacterById(characterId).toModel()
    }

}