package com.slowerror.rickandmorty.ui.episode_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.slowerror.rickandmorty.domain.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    episodeRepository: EpisodeRepository
) : ViewModel() {

    val episodeList = episodeRepository.getEpisodeList().cachedIn(viewModelScope)

}