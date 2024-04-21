package com.slowerror.rickandmorty.ui.search_character_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentSearchCharacterListBinding
import com.slowerror.rickandmorty.domain.common.CustomException
import com.slowerror.rickandmorty.ui.base.BaseFragment
import com.slowerror.rickandmorty.ui.base.LoadStateAdapter
import com.slowerror.rickandmorty.ui.base.VIEW_TYPE_NORMAL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

        initAdapter()

        binding.searchEditText.doAfterTextChanged {
            inputSearchRequest()
        }

        binding.reloadLayout.retryButton.setOnClickListener {
            searchCharacterListAdapter.retry()
        }

        searchCharacterListAdapter.loadStateFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { loadState ->
                val sourceRefresh = loadState.source.refresh
                val sourceAppend = loadState.source.append
                val sourcePrepend = loadState.source.prepend

                binding.progressBar.isVisible = sourceRefresh is LoadState.Loading
                binding.reloadLayout.root.isVisible = sourceRefresh is LoadState.Error
                binding.characterListRw.isVisible = sourceRefresh is LoadState.NotLoading

                val errorState = sourceAppend as? LoadState.Error
                    ?: sourcePrepend as? LoadState.Error
                    ?: sourceRefresh as? LoadState.Error

                if (sourceRefresh is LoadState.Loading && sourceAppend is LoadState.NotLoading) {
                    binding.characterListRw.scrollToPosition(0)
                }

                errorState?.let { handleCustomError(it.error) }

            }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchRequestResult.collectLatest {
                    searchCharacterListAdapter.submitData(it)
                }
            }
        }

    }

    private fun handleCustomError(error: Throwable) {
        when (error) {
            CustomException.IncorrectRequest -> {
                with(binding.reloadLayout) {
                    noConnectionImageView.setImageResource(R.drawable.ic_magnify_remove_64)
                    noConnectionTextView.text = getString(R.string.incorrect_request)
                    descriptionExcTextView.isVisible = false
                    retryButton.isVisible = false
                }

            }

            else -> {
                with(binding.reloadLayout) {
                    noConnectionImageView.setImageResource(R.drawable.ic_no_connection_64)
                    noConnectionTextView.text = getString(R.string.no_connection)
                    descriptionExcTextView.isVisible = true
                    retryButton.isVisible = true
                }

                showShortToast(requireContext(), error.localizedMessage)
            }

        }
    }

    private fun initAdapter() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (searchCharacterListAdapter.getItemViewType(position) == VIEW_TYPE_NORMAL)
                        1 else spanCount
                }
            }
        }

        binding.characterListRw.layoutManager = gridLayoutManager
        binding.characterListRw.adapter = searchCharacterListAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { searchCharacterListAdapter.retry() }
        )
    }

    private fun inputSearchRequest() {
        binding.searchEditText.text?.trim().toString().let {
            viewModel.onSearchRequestChanged(it)
        }
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