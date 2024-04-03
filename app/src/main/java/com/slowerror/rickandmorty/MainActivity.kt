package com.slowerror.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.slowerror.rickandmorty.ui.character_details.CharacterDetailsFragment
import com.slowerror.rickandmorty.ui.character_list.CharacterListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, CharacterListFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}