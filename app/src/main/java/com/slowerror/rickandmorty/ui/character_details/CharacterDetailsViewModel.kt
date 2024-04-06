package com.slowerror.rickandmorty.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.rickandmorty.ui.State
import com.slowerror.rickandmorty.data.repository.CharacterRepository
import com.slowerror.rickandmorty.model.Character
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

    private val _characterDetailsState = MutableStateFlow<State<Character>>(State.loading())
    val characterDetailsState = _characterDetailsState.asStateFlow()

    fun getCharacter(id: Int) = viewModelScope.launch {
        _characterDetailsState.update { State.loading() }
        try {
            val response = characterRepository.getCharacterById(id)
            _characterDetailsState.update {
                State.success(response)
            }
        } catch (e: Exception) {
            _characterDetailsState.update {
                State.error(e.message.orEmpty())
            }
        }
    }
}