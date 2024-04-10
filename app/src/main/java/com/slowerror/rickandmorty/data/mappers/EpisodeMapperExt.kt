package com.slowerror.rickandmorty.data.mappers

import com.slowerror.rickandmorty.data.api.dto.GetEpisodeByIdResponse
import com.slowerror.rickandmorty.model.Episode


fun GetEpisodeByIdResponse.toModel(): Episode {
    return Episode(id, name, airDate, episode, characters, url, created)
}