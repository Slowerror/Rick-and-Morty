package com.slowerror.rickandmorty.di

import com.slowerror.rickandmorty.data.repository.CharacterRepository
import com.slowerror.rickandmorty.data.repository.CharacterRepositoryImpl
import com.slowerror.rickandmorty.data.repository.EpisodeRepository
import com.slowerror.rickandmorty.data.repository.EpisodeRepositoryImpl
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
    abstract fun bindCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(episodeRepositoryImpl: EpisodeRepositoryImpl): EpisodeRepository
}