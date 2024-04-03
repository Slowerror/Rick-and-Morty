package com.slowerror.rickandmorty.di

import androidx.paging.PagingSource
import com.slowerror.rickandmorty.data.api.CharacterPagingSource
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.repository.CharacterRepository
import com.slowerror.rickandmorty.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun bindsCharacterPagingSource(dataSource: CharacterPagingSource): PagingSource<Int, GetCharacterByIdResponse>
}