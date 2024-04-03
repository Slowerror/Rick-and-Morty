package com.slowerror.rickandmorty.ui.character_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.slowerror.rickandmorty.databinding.CharacterItemBinding
import com.slowerror.rickandmorty.model.Character

class CharacterListAdapter(
    private val characterOnClickInterface: CharacterOnClickInterface
) :
    PagingDataAdapter<Character, CharacterListAdapter.CharacterViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, characterOnClickInterface) }
    }

    class CharacterViewHolder(
        private val binding: CharacterItemBinding,

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Character, characterOnClickInterface: CharacterOnClickInterface) {

            binding.apply {
                characterImage.load(item.image)
                characterNameTextView.text = item.name

                root.setOnClickListener {
                    characterOnClickInterface.onClick(item.id)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CharacterItemBinding.inflate(inflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
}