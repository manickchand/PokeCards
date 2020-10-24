package com.manickchand.pokecards.ui.main

import com.manickchand.pokecards.model.PokemonModel

interface MainListener {
    fun clickPokemon(pokemonModel: PokemonModel)
}