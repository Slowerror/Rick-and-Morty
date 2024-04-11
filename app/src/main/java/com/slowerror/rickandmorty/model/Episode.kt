package com.slowerror.rickandmorty.model


data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val episode: String = "",
    val characters: List<Character> = emptyList()
)
