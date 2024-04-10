package com.slowerror.rickandmorty.ui.episode_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slowerror.rickandmorty.R

class EpisodeListFragment : Fragment() {

    companion object {
        fun newInstance() = EpisodeListFragment()
    }

    private lateinit var viewModel: EpisodeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EpisodeListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}