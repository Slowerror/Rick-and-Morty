package com.slowerror.rickandmorty.ui.character_details

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.data.api.NetworkService
import com.slowerror.rickandmorty.data.dto.GetCharacterByIdResponse
import com.slowerror.rickandmorty.databinding.FragmentCharacterDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = NetworkService.networkApi

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val content = api.getCharacterById(2)

            launch(Dispatchers.Main) {
                if (content.isSuccessful) {
                    content.body()?.let { character ->
                        setBinding(character)
                    }
                }
            }

        }


    }

    private fun setBinding(character: GetCharacterByIdResponse) {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}