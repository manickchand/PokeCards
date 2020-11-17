package com.manickchand.pokecards.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.repository.PokeCardsRemoteSource
import com.manickchand.pokecards.repository.RetrofitInit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainViewModel : ViewModel() {

    private val mRetrofit: Retrofit = RetrofitInit.getClient()
    private val pokeCardsRemoteSource: PokeCardsRemoteSource = mRetrofit.create(
        PokeCardsRemoteSource::class.java)

    private val pokemonLiveData = MutableLiveData<List<PokemonModel>>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val allPokemonsList = ArrayList<PokemonModel>()

    fun getPokemonLiveData() = pokemonLiveData as LiveData<List<PokemonModel>>
    fun getErrorLiveData() = errorLiveData as LiveData<Boolean>

    fun fetchPokemons() {

        var call = pokeCardsRemoteSource.getPokemons()

        call.enqueue(object : Callback<List<PokemonModel>> {
            override fun onResponse(
                call: Call<List<PokemonModel>>?,
                response: Response<List<PokemonModel>>?
            ) {
                allPokemonsList.addAll(response?.body() ?: emptyList())
                pokemonLiveData.value = allPokemonsList
                errorLiveData.value = false
            }

            override fun onFailure(call: Call<List<PokemonModel>>?, t: Throwable?) {
                errorLiveData.value = true
            }
        })
    }

    fun filterList(filterStr: String?){
        val pokemonsFiltered = filterStr?.run {
            allPokemonsList.filter { it.name.toLowerCase().contains(filterStr.toLowerCase()) }
        } ?: allPokemonsList
        pokemonLiveData.value = pokemonsFiltered
    }

}