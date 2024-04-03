package com.slowerror.rickandmorty.ui.character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.slowerror.rickandmorty.data.repository.CharacterRepository
import com.slowerror.rickandmorty.model.Character
import com.slowerror.rickandmorty.ui.character_details.CharacterDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _characterListState = MutableStateFlow(CharacterListState())
    val characterListState = _characterListState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() = viewModelScope.launch {
        _characterListState.update { it.copy(isLoading = true) }
        characterRepository.getCharacterList().collectLatest {
            _characterListState.update {state ->
                Log.i("CharacterListFragment", state.characterList.toString())
                state.copy(isLoading = false, characterList = it)
            }
        }
    }


}