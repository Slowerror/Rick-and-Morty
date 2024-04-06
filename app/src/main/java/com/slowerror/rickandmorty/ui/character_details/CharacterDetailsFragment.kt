package com.slowerror.rickandmorty.ui.character_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.ui.State
import com.slowerror.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.slowerror.rickandmorty.model.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailsBinding.bind(view)
        viewModel.getCharacter(args.characterId)

        viewModel.characterDetailsState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                when (uiState) {
                    is State.Error -> {
                        onVisibleView(false)
                        if (uiState.message.isNotEmpty()) {
                            Snackbar.make(
                                requireView(),
                                "Произошла ошибка: ${uiState.message}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is State.Loading -> {
                        onVisibleView(true)
                    }
                    is State.Success -> {
                        onVisibleView(false)
                        setBinding(uiState.data)
                    }
                }



            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun setBinding(character: Character?) {
        if (character == null) return
        binding.apply {
            nameTextView.text = character.name
            statusTextView.text = character.status
            characterImage.load(character.image)
            originTextView.text = character.origin.name
            speciesTextView.text = character.species

            when (character.gender) {
                "Female" -> genderIcon.setImageResource(R.drawable.ic_female_24)
                "Male" -> genderIcon.setImageResource(R.drawable.ic_male_24)
            }
        }
    }

    private fun onVisibleView(loading: Boolean) {
        if (loading) {
            binding.apply {
                progressBar.isVisible = true

                nameTextView.isVisible = false
                statusTextView.isVisible = false
                characterImage.isVisible = false
                genderIcon.isVisible = false

                originHeaderTextView.isVisible = false
                originTextView.isVisible = false

                speciesHeaderTextView.isVisible = false
                speciesTextView.isVisible = false
            }
        } else {
            binding.apply {
                progressBar.isVisible = false

                nameTextView.isVisible = true
                statusTextView.isVisible = true
                characterImage.isVisible = true
                genderIcon.isVisible = true

                originHeaderTextView.isVisible = true
                originTextView.isVisible = true

                speciesHeaderTextView.isVisible = true
                speciesTextView.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}