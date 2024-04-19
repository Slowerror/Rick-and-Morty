package com.slowerror.rickandmorty.ui.character_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentCharacterListBinding
import com.slowerror.rickandmorty.ui.base.BaseFragment
import com.slowerror.rickandmorty.ui.base.LoadStateAdapter
import com.slowerror.rickandmorty.ui.base.VIEW_TYPE_NORMAL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CharacterListFragment :
    BaseFragment(R.layout.fragment_character_list),
    CharacterOnClickInterface {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by viewModels()

    private val characterListAdapter by lazy {
        CharacterListAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterListBinding.bind(view)

        initAdapter()

        binding.reloadLayout.retryButton.setOnClickListener {
            characterListAdapter.retry()
        }

        characterListAdapter.loadStateFlow
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

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.characterList
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { characterListAdapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (characterListAdapter.getItemViewType(position) == VIEW_TYPE_NORMAL)
                        1 else spanCount
                }
            }
        }

        binding.characterListRw.layoutManager = gridLayoutManager
        binding.characterListRw.adapter = characterListAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { characterListAdapter.retry() }
        )
    }

    override fun onClickToItem(characterId: Int) {
        findNavController().navigate(
            CharacterListFragmentDirections
                .actionCharacterListFragmentToCharacterDetailsFragment(characterId)
        )
    }

}