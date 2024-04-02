package com.slowerror.rickandmorty.di

import com.slowerror.rickandmorty.data.api.Api
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.api.RemoteService
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
    fun provideNetworkApi(): Api {
        return RemoteService.api
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: Api): RemoteDataSource {
        return RemoteDataSource(api)
    }

}