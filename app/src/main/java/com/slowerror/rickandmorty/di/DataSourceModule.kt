package com.slowerror.rickandmorty.di

import androidx.paging.PagingSource
import com.slowerror.rickandmorty.data.api.CharacterPagingDataSource
import com.slowerror.rickandmorty.data.api.EpisodePagingDataSource
import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCharacterPagingSource(dataSource: CharacterPagingDataSource): PagingSource<Int, GetCharacterByIdResponse>

    @Binds
    abstract fun bindEpisodePagingSource(dataSource: EpisodePagingDataSource): PagingSource<Int, GetEpisodeByIdResponse>
}