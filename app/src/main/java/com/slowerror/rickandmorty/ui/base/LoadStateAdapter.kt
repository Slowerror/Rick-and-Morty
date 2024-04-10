package com.slowerror.rickandmorty.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slowerror.rickandmorty.R
import com.slowerror.rickandmorty.databinding.LayoutReloadResponseBinding
import com.slowerror.rickandmorty.ui.base.LoadStateAdapter.LoadViewHolder

class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        return LoadViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    class LoadViewHolder(
        private val binding: LayoutReloadResponseBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            /*if (loadState is LoadState.Error) {
                binding.noConnectionTextView.text = loadState.error.localizedMessage
            }*/

            binding.root.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_reload_response, parent, false)
                val binding = LayoutReloadResponseBinding.bind(view)
                return LoadViewHolder(binding, retry)
            }
        }
    }

}