package com.slowerror.rickandmorty.ui.episode_details

import com.slowerror.rickandmorty.domain.model.Episode

data class EpisodeDetailsState(
    val isLoading: Boolean = false,
    val data: Episode? = null,
    val errorMessage: String? = null
)