package com.slowerror.rickandmorty.ui.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.slowerror.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    characterRepository: CharacterRepository
) : ViewModel() {

    val characterList = characterRepository.getCharacterList().cachedIn(viewModelScope)


}