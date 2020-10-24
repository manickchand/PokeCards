package com.manickchand.pokecards.repository

import com.manickchand.pokecards.model.PokemonModel
import retrofit2.Call
import retrofit2.http.GET

interface IServiceRetrofit {

    @GET("pokemons.json")
    fun getPokemons(): Call<List<PokemonModel>>

}