package com.slowerror.rickandmorty.data.mappers

import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import com.slowerror.rickandmorty.model.Character

typealias originDto = GetCharacterByIdResponse.Origin
typealias locationDto = GetCharacterByIdResponse.Location

fun GetCharacterByIdResponse.toModel(episodes: List<GetEpisodeByIdResponse> = emptyList()): Character {
    return Character(
        id,
        name,
        status,
        species,
        type,
        gender,
        origin.toModel(),
        location.toModel(),
        image,
        episode = episodes.toEpisodeList()
    )
}

fun List<GetCharacterByIdResponse>.toCharacterList(): List<Character> {
    return this.map { it.toModel() }
}

fun originDto.toModel(): Character.Origin {
    return Character.Origin(name, url)
}

fun locationDto.toModel(): Character.Location {
    return Character.Location(name, url)
}