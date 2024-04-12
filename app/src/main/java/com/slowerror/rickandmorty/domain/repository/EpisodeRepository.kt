package com.slowerror.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.slowerror.rickandmorty.domain.common.Resource
import com.slowerror.rickandmorty.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    suspend fun getEpisodeById(episodeId: Int): Resource<Episode>

    fun getEpisodeList(): Flow<PagingData<Episode>>

}