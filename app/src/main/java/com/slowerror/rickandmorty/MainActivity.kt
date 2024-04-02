package com.slowerror.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.slowerror.rickandmorty.data.api.NetworkService
import com.slowerror.rickandmorty.ui.character_details.CharacterDetailsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, CharacterDetailsFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}