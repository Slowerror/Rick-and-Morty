package com.slowerror.rickandmorty.ui.character_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.EpisodeItemBinding
import com.slowerror.rickandmorty.model.Episode

class CharacterWithEpisodeAdapter : ListAdapter<Episode, CharacterWithEpisodeAdapter.CharacterWithEpisodeViewHolder>(CharacterWithEpisodeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterWithEpisodeViewHolder {
        return CharacterWithEpisodeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterWithEpisodeViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    class CharacterWithEpisodeViewHolder(
        private val binding: EpisodeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Episode) {
            with(binding) {
                episodeTextView.text = item.episode
                dataTextView.text = item.airDate

                nameTextView.text = itemView.context.getString(
                    R.string.name_episode_with_number,
                    item.name,
                    item.id
                )
            }
        }

        companion object {
            fun create(parent: ViewGroup): CharacterWithEpisodeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = EpisodeItemBinding.inflate(inflater, parent, false)
                return CharacterWithEpisodeViewHolder(binding)
            }
        }
    }

    object CharacterWithEpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }

    }
}