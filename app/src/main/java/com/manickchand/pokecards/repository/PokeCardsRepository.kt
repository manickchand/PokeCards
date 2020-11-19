package com.manickchand.pokecards.repository

import com.manickchand.pokecards.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PokeCardsRepository {
    suspend fun getPokemons() : List<PokemonModel>
}

class PokeCardsRepositoryImpl(private val pokeCardsRemoteSource: PokeCardsRemoteSource) : PokeCardsRepository{
    override suspend fun getPokemons(): List<PokemonModel> {
        return withContext(Dispatchers.IO){
            pokeCardsRemoteSource.getPokemons()
        }
    }
}