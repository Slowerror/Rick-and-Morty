package com.slowerror.rickandmorty.ui.character_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.slowerror.rickandmorty.model.Character
import com.slowerror.rickandmorty.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment(R.layout.fragment_character_details) {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailsBinding.bind(view)
        viewModel.getCharacter(args.characterId)

        binding.reloadLayout.retryButton.setOnClickListener {
            viewModel.getCharacter(args.characterId)
        }

        viewModel.characterDetailsState
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

                        showLongSnackBar(requireView(), state.errorMessage)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun setBinding(character: Character?) {
        if (character == null) return
        binding.apply {
            nameTextView.text = character.name
            statusTextView.text =
                getString(R.string.status_with_species, character.status, character.species)

            characterImage.load(character.image)
            originTextView.text = character.origin.name
            speciesTextView.text = character.species

            val statusColor = getStatusColor(character.status)
            statusIcon.setColorFilter(statusColor)

            when (character.gender) {
                "Female" -> genderIcon.setImageResource(R.drawable.ic_female_24)
                "Male" -> genderIcon.setImageResource(R.drawable.ic_male_24)
            }

            episodeListRw.adapter = CharacterWithEpisodeAdapter().apply {
                submitList(character.episode)
            }
        }
    }

    private fun getStatusColor(status: String): Int {
        return when (status) {
            "Alive" -> requireContext().getColor(R.color.lime_green)
            "Dead" -> requireContext().getColor(R.color.red)
            else -> requireContext().getColor(R.color.silver)
        }
    }

    private fun setIsVisibleView(isVisible: Boolean) {
        binding.apply {
            nameTextView.isVisible = isVisible
            statusTextView.isVisible = isVisible
            characterImage.isVisible = isVisible

            genderIcon.isVisible = isVisible
            statusIcon.isVisible = isVisible

            episodesHeaderTextView.isVisible = isVisible
            episodeListRw.isVisible = isVisible

            originHeaderTextView.isVisible = isVisible
            originTextView.isVisible = isVisible

            speciesHeaderTextView.isVisible = isVisible
            speciesTextView.isVisible = isVisible
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}