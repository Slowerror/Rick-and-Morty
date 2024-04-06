package com.slowerror.rickandmorty.ui.character_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.slowerror.rickandmorty.databinding.FragmentCharacterListBinding
import com.slowerror.rickandmorty.ui.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val I_TAG = "CharacterListFragment"

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.fragment_character_list), CharacterOnClickInterface {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterListBinding.bind(view)

        val characterListAdapter = CharacterListAdapter(this)

        characterListAdapter.addLoadStateListener { loadState ->

            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

            when {
                loadState.refresh is LoadState.Error -> {
                    Log.i(I_TAG, "loadState.refresh")
                    showError(loadState.refresh as LoadState.Error)
                }
                loadState.append is LoadState.Error-> {
                    Log.i(I_TAG, "loadState.append")
                    showError(loadState.append as LoadState.Error)
                }
                loadState.prepend is LoadState.Error -> {
                    Log.i(I_TAG, "loadState.prepend")
                    showError(loadState.prepend as LoadState.Error)
                }
            }
        }
        binding.characterListRw.adapter = characterListAdapter

        viewModel.characterList
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { characterListAdapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickToItem(characterId: Int) {
        findNavController().navigate(
            CharacterListFragmentDirections
                .actionCharacterListFragmentToCharacterDetailsFragment(characterId)
        )
    }

    private fun showError(errorState: LoadState.Error?) {
        errorState?.let {
            Log.i(I_TAG, "${it.error.message}")
            Snackbar.make(requireView(), "${it.error.message}", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

}