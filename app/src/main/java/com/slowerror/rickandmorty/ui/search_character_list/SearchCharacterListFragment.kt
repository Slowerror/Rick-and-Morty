package com.slowerror.rickandmorty.ui.search_character_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.flatMap
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentSearchCharacterListBinding
import com.slowerror.rickandmorty.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCharacterListFragment : BaseFragment(R.layout.fragment_search_character_list) {

    private var _binding: FragmentSearchCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchCharacterListViewModel by viewModels()
    private val searchCharacterListAdapter by lazy {
        SearchCharacterListAdapter(::onClickToItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchCharacterListBinding.bind(view)


        binding.characterListRw.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.characterListRw.adapter = searchCharacterListAdapter

        binding.searchEditText.doAfterTextChanged { inputString ->
            viewModel.onSearchRequestChanged(inputString.toString().trim())
            /*inputString.toString().trim().let {
                if (it.isNotBlank())
                    viewModel.onSearchRequestChanged(it)
            }*/
        }

        /*viewModel.searchRequest.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
            }.launchIn(viewLifecycleOwner.lifecycleScope)*/

        searchCharacterListAdapter.loadStateFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { loadState ->

                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.reloadLayout.root.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.source.refresh as? LoadState.Error

                errorState?.let {
                    showLongSnackBar(requireView(), it.error.localizedMessage)
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchRequestResult.collectLatest {
                    searchCharacterListAdapter.submitData(it)
                }
            }
        }
        /*viewModel.searchRequestResult
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                searchCharacterListAdapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)*/
    }

    private fun onClickToItem(characterId: Int) {
        findNavController().navigate(
            SearchCharacterListFragmentDirections
                .actionSearchCharacterListFragmentToCharacterDetailsFragment(characterId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}