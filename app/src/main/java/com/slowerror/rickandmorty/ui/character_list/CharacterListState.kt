package com.slowerror.rickandmorty.ui.character_list

import androidx.paging.PagingData
import com.slowerror.rickandmorty.model.Character

data class CharacterListState(
    val isLoading: Boolean = false,
    val characterList: PagingData<Character> = PagingData.empty()
)