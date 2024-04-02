package com.slowerror.rickandmorty.ui.character_details

import com.slowerror.rickandmorty.model.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val character: Character = Character()
)
