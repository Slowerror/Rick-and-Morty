package com.slowerror.rickandmorty.ui.character_details

import com.slowerror.rickandmorty.domain.model.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val data: Character? = null,
    val errorMessage: String? = null
)
