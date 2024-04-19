package com.slowerror.rickandmorty.ui.search_character_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.slowerror.rickandmorty.databinding.CharacterItemBinding
import com.slowerror.rickandmorty.domain.model.Character
import com.slowerror.rickandmorty.ui.base.VIEW_TYPE_FOOTER
import com.slowerror.rickandmorty.ui.base.VIEW_TYPE_NORMAL
import com.slowerror.rickandmorty.ui.search_character_list.SearchCharacterListAdapter.SearchCharacterViewHolder

class SearchCharacterListAdapter(
    private val onClick: (Int) -> Unit
) :
    PagingDataAdapter<Character, SearchCharacterViewHolder>(SearchCharacterDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCharacterViewHolder {
        return SearchCharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchCharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onClick) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) VIEW_TYPE_FOOTER else VIEW_TYPE_NORMAL
    }

    class SearchCharacterViewHolder(
        private val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Character, onClick: (Int) -> Unit) {
            binding.characterImage.load(item.image)
            binding.characterNameTextView.text = item.name

            itemView.setOnClickListener { onClick.invoke(item.id) }
        }

        companion object {
            fun create(parent: ViewGroup): SearchCharacterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CharacterItemBinding.inflate(inflater, parent, false)
                return SearchCharacterViewHolder(binding)
            }
        }
    }

    object SearchCharacterDiffUtil : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
}
