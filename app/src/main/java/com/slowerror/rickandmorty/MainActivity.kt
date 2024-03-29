package com.slowerror.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.slowerror.rickandmorty.data.api.NetworkService
import com.slowerror.rickandmorty.data.dto.Character
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = NetworkService.networkApi

        lifecycleScope.launch {
            api.getCharacters()
        }


    }
}