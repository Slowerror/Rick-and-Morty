package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.slowerror.rickandmorty.common.Resource
import com.slowerror.rickandmorty.data.api.RemoteDataSource
import com.slowerror.rickandmorty.data.mappers.toModel
import com.slowerror.rickandmorty.data.safeApiCall
import com.slowerror.rickandmorty.model.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : EpisodeRepository {

    override suspend fun getEpisodeById(episodeId: Int): Resource<Episode> =
        safeApiCall {
            remoteDataSource.getEpisodeById(episodeId).body()?.toModel() ?: Episode()
        }


    override fun getEpisodeList(): Flow<PagingData<Episode>> =
        remoteDataSource.getEpisodeList().map { pagingData ->
            pagingData.map { it.toModel() }
        }

}