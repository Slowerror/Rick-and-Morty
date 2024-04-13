package com.slowerror.rickandmorty.ui.search_character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.slowerror.rickandmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class SearchCharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val searchRequest = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchRequestResult = searchRequest.flatMapLatest { request ->
            characterRepository.getCharacterListByName(request)
        }.cachedIn(viewModelScope)


    fun onSearchRequestChanged(userRequest: String) {
        searchRequest.value = userRequest
    }

}