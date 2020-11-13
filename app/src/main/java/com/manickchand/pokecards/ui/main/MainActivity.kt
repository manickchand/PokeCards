package com.manickchand.pokecards.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.manickchand.pokecards.R
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.repository.PokeCardsRemoteSource
import com.manickchand.pokecards.repository.RetrofitInit.getClient
import com.manickchand.pokecards.ui.detail.DetailDialogFragment
import com.manickchand.pokecards.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), MainListener {

    private val mRetrofit: Retrofit = getClient()
    private val pokeCardsRemoteSource: PokeCardsRemoteSource = this.mRetrofit.create(PokeCardsRemoteSource::class.java)
    private val pokemonsList = ArrayList<PokemonModel>()
    private val allPokemonsList = ArrayList<PokemonModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRefresh()
        setupRecycler()
        getPokemons()

        et_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }
        })

    }

    private fun setupRefresh(){
        load.setOnRefreshListener{
            getPokemons()
        }
    }

    private fun getPokemons() {

        load.isRefreshing = true

        var call = this.pokeCardsRemoteSource.getPokemons()

        call.enqueue(object : Callback<List<PokemonModel>> {
            override fun onResponse(
                call: Call<List<PokemonModel>>?,
                response: Response<List<PokemonModel>>?
            ) {
                load.isRefreshing = false
                addItems(response?.body() ?: emptyList())
                showItems(allPokemonsList)
            }

            override fun onFailure(call: Call<List<PokemonModel>>?, t: Throwable?) {
                load.isRefreshing = false
                showToast("Erro ao carregar pokemons")
            }
        })
    }

    private fun setupRecycler() {
        with(recycler) {
            setHasFixedSize(true)
            adapter = MainAdapter(pokemonsList, this@MainActivity)
        }
    }

    private fun showItems(pokemons: List<PokemonModel>){
        pokemonsList.clear()
        pokemonsList.addAll(pokemons)
        recycler.adapter?.notifyDataSetChanged()
    }

    private fun addItems(pokemons: List<PokemonModel>){
        et_filter.text.clear()
        allPokemonsList.clear()
        allPokemonsList.addAll(pokemons)
    }

    override fun clickPokemon(pokemonModel: PokemonModel) {
        DetailDialogFragment.newInstance(pokemonModel).show(supportFragmentManager, "Card Pokemon")
    }

    private fun filterList(filterStr: String?){
     val pokemonsFiltered = filterStr?.run {
        allPokemonsList.filter { it.name.toLowerCase().contains(filterStr.toLowerCase()) }
     } ?: allPokemonsList
        showItems(pokemonsFiltered)
    }

}