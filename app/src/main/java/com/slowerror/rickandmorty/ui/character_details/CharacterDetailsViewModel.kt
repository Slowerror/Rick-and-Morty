package com.slowerror.rickandmorty.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.rickandmorty.common.Resource
import com.slowerror.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getCharacter(id: Int) = viewModelScope.launch {
        _characterDetailsState.update { it.copy(isLoading = true) }

        when (val response = characterRepository.getCharacterById(id)) {
            is Resource.Error -> {
                _characterDetailsState.update {
                    it.copy(
                        isLoading = false,
                        data = CharacterDetailsState.emptyData(),
                        errorMessage = response.message
                    )
                }
            }
            is Resource.Success -> {
                _characterDetailsState.update {
                    it.copy(
                        isLoading = false,
                        data = response.data,
                        errorMessage = null
                    )
                }
            }
        }

    }
}