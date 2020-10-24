package com.manickchand.pokecards.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.manickchand.pokecards.R

fun getPokemonColor(context: Context, type: String) : Int {
        val color = when (type.toLowerCase()) {
                "grass", "bug" -> R.color.lightTeal
                "fire" -> R.color.lightRed
                "water", "fighting", "normal" -> R.color.lightBlue
                "electric", "psychic" -> R.color.lightYellow
                "poison", "ghost" -> R.color.lightPurple
                "ground", "rock" -> R.color.lightBrown
                "dark" -> R.color.black
                else -> R.color.lightBlue
        }

        return ContextCompat.getColor(context, color)
}