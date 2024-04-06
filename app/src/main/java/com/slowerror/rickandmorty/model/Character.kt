package com.slowerror.rickandmorty.model

import android.content.Context
import com.slowerror.rickandmorty.R


data class Character(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: Origin = Origin(),
    val location: Location = Location(),
    val image: String = "",
    val episode: List<String> = emptyList()
) {
    data class Location(
        val name: String = "",
        val url: String = ""
    )

    data class Origin(
        val name: String = "",
        val url: String = ""
    )

    fun statusWithSpecies(): String = "$status - $species"


    fun statusColor(context: Context): Int {
        return when(status) {
            "Alive" -> context.getColor(R.color.lime_green)
            "Dead" -> context.getColor(R.color.red)
            else -> context.getColor(R.color.silver)
        }
    }
}
