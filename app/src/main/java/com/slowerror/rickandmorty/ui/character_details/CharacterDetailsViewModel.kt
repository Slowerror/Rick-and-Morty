package com.slowerror.rickandmorty.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getCharacter(id: Int) {
        _characterDetailsState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val data = characterRepository.getCharacterById(id)

            _characterDetailsState.update {
                it.copy(
                    isLoading = false,
                    character = data
                )
            }
        }
    }
}