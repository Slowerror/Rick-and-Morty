package com.slowerror.rickandmorty.ui.character_details

import com.slowerror.rickandmorty.model.Character

data class CharacterDetailsState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null
)
