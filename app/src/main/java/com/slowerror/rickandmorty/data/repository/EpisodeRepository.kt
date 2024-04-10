package com.slowerror.rickandmorty.data.repository

import androidx.paging.PagingData
import com.slowerror.rickandmorty.common.Resource
import com.slowerror.rickandmorty.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    suspend fun getEpisodeById(episodeId: Int): Resource<Episode>

    fun getEpisodeList(): Flow<PagingData<Episode>>

}