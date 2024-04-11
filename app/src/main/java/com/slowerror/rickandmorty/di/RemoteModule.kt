package com.slowerror.rickandmorty.di

import com.slowerror.rickandmorty.data.api.RemoteService
import com.slowerror.rickandmorty.data.api.CharacterPagingDataSource
import com.slowerror.rickandmorty.data.api.EpisodePagingDataSource
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideNetworkApi(): RemoteService {
        return RemoteService.runService()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        characterDataSource: CharacterPagingDataSource,
        episodeDataSource: EpisodePagingDataSource,
        remoteService: RemoteService
    ): RemoteDataSource {
        return RemoteDataSource(
            characterPagingDataSource = characterDataSource,
            episodePagingDataSource = episodeDataSource,
            remoteService = remoteService
        )
    }
}