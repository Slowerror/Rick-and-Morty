package com.slowerror.rickandmorty.ui.episode_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.slowerror.rickandmorty.model.Episode
import com.slowerror.rickandmorty.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EpisodeDetailsFragment : BottomSheetDialogFragment(R.layout.fragment_episode_details) {

    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodeDetailsViewModel by viewModels()
    private val safeArgs: EpisodeDetailsFragmentArgs by navArgs()
    private val episodeWithCharacterAdapter by lazy { EpisodeWithCharacterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeDetailsBinding.bind(view)

        viewModel.fetchEpisode(safeArgs.episodeId)

        binding.reloadLayout.retryButton.setOnClickListener {
            viewModel.fetchEpisode(safeArgs.episodeId)
        }

        binding.characterListRw.adapter = EpisodeWithCharacterAdapter()

        viewModel.episodeDetailsState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when {
                    state.isLoading -> {
                        binding.progressBar.isVisible = true
                        binding.reloadLayout.root.isVisible = false
                        setIsVisibleView(false)
                    }

                    state.data != null -> {
                        binding.progressBar.isVisible = false
                        binding.reloadLayout.root.isVisible = false
                        setIsVisibleView(true)

                        setBinding(state.data)
                    }

                    state.errorMessage != null -> {
                        binding.progressBar.isVisible = false
                        binding.reloadLayout.root.isVisible = true
                        setIsVisibleView(false)

                        showErrorMessage(state.errorMessage)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun setBinding(data: Episode) {
        with(binding) {
            nameTextView.text = getString(R.string.number_episode_with_name, data.id, data.name)
            episodeTextView.text = data.episode
            airDataTextView.text = data.airDate
            episodeWithCharacterAdapter.submitList(data.characters)
        }
    }

    private fun setIsVisibleView(isVisible: Boolean) {
        with(binding) {
            nameTextView.isVisible = isVisible
            episodeTextView.isVisible = isVisible
            dividerView.isVisible = isVisible
            airDataTextView.isVisible = isVisible
            characterListRw.isVisible = isVisible
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}