package com.slowerror.rickandmorty.ui.episode_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.EpisodeItemBinding
import com.slowerror.rickandmorty.domain.model.Episode
import com.slowerror.rickandmorty.ui.episode_list.EpisodeListAdapter.EpisodeViewHolder

class EpisodeListAdapter(
    private val episodeOnClickInterface: EpisodeOnClickInterface
) : PagingDataAdapter<Episode, EpisodeViewHolder>(EpisodeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, episodeOnClickInterface) }
    }


    class EpisodeViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Episode, episodeOnClickInterface: EpisodeOnClickInterface) {
            with(binding) {
                episodeTextView.text = item.episode
                dataTextView.text = item.airDate

                nameTextView.text = itemView.context.getString(
                    R.string.name_episode_with_number,
                    item.name,
                    item.id
                )

                itemView.setOnClickListener {
                    episodeOnClickInterface.onClickToItem(item.id)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): EpisodeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = EpisodeItemBinding.inflate(inflater, parent, false)
                return EpisodeViewHolder(binding)
            }
        }
    }


    object EpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {

        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }

    }
}