package com.slowerror.rickandmorty.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.rickandmorty.domain.common.Resource
import com.slowerror.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _characterDetailsState = MutableStateFlow(CharacterDetailsState())
    val characterDetailsState = _characterDetailsState.asStateFlow()

    private var fetchJob: Job? = null

    fun getCharacter(id: Int) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _characterDetailsState.update {
                it.copy(
                    isLoading = true,
                    data = null,
                    errorMessage = null
                )
            }

            when (val response = characterRepository.getCharacterById(id)) {
                is Resource.Success -> {
                    _characterDetailsState.update {
                        it.copy(
                            isLoading = false,
                            data = response.data,
                            errorMessage = null
                        )
                    }
                }

                is Resource.Error -> {
                    _characterDetailsState.update {
                        it.copy(
                            isLoading = false,
                            data = null,
                            errorMessage = response.message
                        )
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