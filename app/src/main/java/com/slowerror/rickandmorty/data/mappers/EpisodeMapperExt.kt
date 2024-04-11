package com.slowerror.rickandmorty.data.mappers

import com.slowerror.rickandmorty.data.api.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import com.slowerror.rickandmorty.model.Episode


fun GetEpisodeByIdResponse.toModel(characters: List<GetCharacterByIdResponse> = emptyList()): Episode {
    return Episode(id, name, airDate, episode,
        characters = characters.map {
            it.toModel()
        }
    )

}