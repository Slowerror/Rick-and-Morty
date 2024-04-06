package com.slowerror.rickandmorty.ui.character_details

import com.slowerror.rickandmorty.model.Character

data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val data: Character = emptyData(),
    val errorMessage: String? = null
) {
    companion object {
        fun emptyData(): Character {
            return Character()
        }
    }
}
