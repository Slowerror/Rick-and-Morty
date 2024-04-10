package com.slowerror.rickandmorty.ui.episode_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentEpisodeListBinding
import com.slowerror.rickandmorty.ui.base.BaseFragment
import com.slowerror.rickandmorty.ui.base.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EpisodeListFragment : BaseFragment(R.layout.fragment_episode_list), EpisodeOnClickInterface {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodeListViewModel by viewModels()

    private val episodeListAdapter by lazy { EpisodeListAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        binding.episodeListRw.adapter = episodeListAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { episodeListAdapter.retry() }
        )

        binding.reloadLayout.retryButton.setOnClickListener {
            episodeListAdapter.retry()
        }

        episodeListAdapter.loadStateFlow
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

        viewModel.episodeList
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { episodeListAdapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickToItem(episodeId: Int) {
        findNavController().navigate(
            EpisodeListFragmentDirections
                .actionEpisodeListFragmentToEpisodeDetailsFragment(episodeId)
        )
    }

}