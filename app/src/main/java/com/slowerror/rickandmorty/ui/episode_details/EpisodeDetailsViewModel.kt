package com.slowerror.rickandmorty.ui.episode_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.rickandmorty.common.Resource
import com.slowerror.rickandmorty.data.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    private val _episodeDetailsState = MutableStateFlow(EpisodeDetailsState())
    val episodeDetailsState = _episodeDetailsState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchEpisode(episodeId: Int) {
        fetchJob = null
        fetchJob = viewModelScope.launch {
            _episodeDetailsState.update {
                it.copy(isLoading = true, data = null, errorMessage = null)
            }

            when (val response = episodeRepository.getEpisodeById(episodeId)) {
                is Resource.Success -> {
                    _episodeDetailsState.update {
                        it.copy(isLoading = false, data = response.data, errorMessage = null)
                    }
                }

                is Resource.Error -> {
                    _episodeDetailsState.update {
                        it.copy(isLoading = false, data = null, errorMessage = response.message)
                    }
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        fetchJob = null
    }
}