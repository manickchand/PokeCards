package com.manickchand.pokecards.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.manickchand.pokecards.R
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.repository.IServiceRetrofit
import com.manickchand.pokecards.repository.RetrofitInit.getClient
import com.manickchand.pokecards.ui.detail.DetailFragment
import com.manickchand.pokecards.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), MainListener {

    private val mRetrofit: Retrofit = getClient()
    private val mIserviceRetrofit: IServiceRetrofit = this.mRetrofit.create(IServiceRetrofit::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPokemons()

    }

    private fun getPokemons() {

        load.isVisible = true

        var call = this.mIserviceRetrofit.getPokemons()

        call.enqueue(object : Callback<List<PokemonModel>> {
            override fun onResponse(
                call: Call<List<PokemonModel>>?,
                response: Response<List<PokemonModel>>?
            ) {
                load.isVisible = false
                setupRecycler(response?.body() ?: emptyList())
            }

            override fun onFailure(call: Call<List<PokemonModel>>?, t: Throwable?) {
                load.isVisible = false
                showToast("Erro ao carregar pokemons")
            }
        })
    }

    private fun setupRecycler(items: List<PokemonModel>) {
        with(recycler) {
            setHasFixedSize(true)
            adapter = MainAdapter(items, this@MainActivity)
        }
    }

    override fun clickPokemon(pokemonModel: PokemonModel) {
        DetailFragment.newInstance(pokemonModel).show(supportFragmentManager, "Card Pokemon")
    }

}