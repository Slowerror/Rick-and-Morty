package com.slowerror.rickandmorty.ui.search_character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.slowerror.rickandmorty.domain.model.Character
import com.slowerror.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchCharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val searchRequest = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchRequestResult: Flow<PagingData<Character>> = searchRequest
        .flatMapLatest { request ->
            when (request.isNotBlank()) {
                true -> {
                    delay(500)
                    characterRepository.getCharacterListByName(request)
                }

                false -> {
                    flowOf(
                        PagingData.empty(
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(true),
                                append = LoadState.NotLoading(true)
                            )
                        )
                    )
                }
            }

        }
        .cachedIn(viewModelScope)


    fun onSearchRequestChanged(userRequest: String) {
        searchRequest.value = userRequest
    }


}