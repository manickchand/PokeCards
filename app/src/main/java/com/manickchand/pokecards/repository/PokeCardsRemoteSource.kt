package com.manickchand.pokecards.repository

import com.manickchand.pokecards.model.PokemonModel
import retrofit2.http.GET

interface PokeCardsRemoteSource {

    @GET("pokemons.json")
    suspend fun getPokemons(): List<PokemonModel>

}