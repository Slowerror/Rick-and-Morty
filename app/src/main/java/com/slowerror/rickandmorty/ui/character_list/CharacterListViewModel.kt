package com.slowerror.rickandmorty.ui.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slowerror.rickandmorty.data.repository.CharacterRepository
import com.slowerror.rickandmorty.model.Character
import com.slowerror.rickandmorty.ui.State
import com.slowerror.rickandmorty.ui.character_details.CharacterDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    /*private val _characterListState =
        MutableStateFlow<State<PagingData<Character>>>(State.loading())
    val characterListState = _characterListState.asStateFlow()*/

    val characterList = characterRepository.getCharacterList().cachedIn(viewModelScope)

    init {
//        getCharacters()
    }

    /*private fun getCharacters() = viewModelScope.launch {
        _characterListState.update { State.loading() }
        try {
            characterRepository.getCharacterList()
                .cachedIn(viewModelScope)
                .collect { data ->
                    _characterListState.update { State.success(data) }
                }

        } catch (e: Exception) {
            _characterListState.update { State.error(e.message.orEmpty()) }
        }

    }*/


}