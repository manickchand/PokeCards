package com.manickchand.pokecards.model

import java.io.Serializable

data class PokemonModel(
    val name: String,
    val imageurl: String,
    val xdescription: String,
    val attack: Int,
    val defense: Int,
    val hp: Int,
    val typeofpokemon: List<String>,
    var color: Int = -1
) : Serializable