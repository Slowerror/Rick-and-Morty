package com.slowerror.rickandmorty.ui.episode_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.slowerror.rickandmorty.databinding.CharacterItemSquareBinding
import com.slowerror.rickandmorty.domain.model.Character
import com.slowerror.rickandmorty.ui.episode_details.EpisodeWithCharacterAdapter.CharacterSquareViewHolder

class EpisodeWithCharacterAdapter : ListAdapter<Character, CharacterSquareViewHolder>(CharacterSquareDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterSquareViewHolder {
        return CharacterSquareViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterSquareViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    class CharacterSquareViewHolder(
        private val binding: CharacterItemSquareBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Character) {
            binding.characterImage.load(item.image)
            binding.characterNameTextView.text = item.name
        }

        companion object {
            fun create(parent: ViewGroup): CharacterSquareViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CharacterItemSquareBinding.inflate(inflater, parent, false)
                return CharacterSquareViewHolder(binding)
            }
        }
    }

    object CharacterSquareDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
}