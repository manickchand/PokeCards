package com.manickchand.pokecards.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.manickchand.pokecards.R
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.ui.detail.DetailDialogFragment
import com.manickchand.pokecards.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainListener {

    private val viewModel: MainViewModel by viewModel()
    private val pokemonsList = ArrayList<PokemonModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRefresh()
        setupRecycler()
        bindObservables()
        setupFilter()
        viewModel.fetchPokemons(this)

    }

    private fun bindObservables(){
        viewModel.getPokemonLiveData().observe(this, { pokemons ->
            load.isRefreshing = false
            showItems(pokemons)
        })

        viewModel.getErrorLiveData().observe(this, { isError ->
            load.isRefreshing = false
            if(isError) showToast("Erro ao carregar pokemons")
        })
    }

    private fun setupRefresh(){
        load.isRefreshing = true
        load.setOnRefreshListener{
            viewModel.fetchPokemons(this)
        }
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

    override fun clickPokemon(pokemonModel: PokemonModel) {
        DetailDialogFragment.newInstance(pokemonModel).show(supportFragmentManager, "Card Pokemon")
    }

    private fun setupFilter(){
        et_filter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterList(s.toString())
            }
        })
    }

}