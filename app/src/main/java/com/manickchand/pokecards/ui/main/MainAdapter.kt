package com.manickchand.pokecards.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manickchand.pokecards.R
import com.manickchand.pokecards.utils.loadGlideImage
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.utils.getPokemonColor
import kotlinx.android.synthetic.main.item_pokemon.view.*

class MainAdapter( private val items: List<PokemonModel>, private val listener: MainListener) : RecyclerView.Adapter<MainAdapter.ViewHolder?>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(inflateLayout(parent)) {

        fun bind(pokemon: PokemonModel) = with(itemView) {

            pokemon.run {
                val color = getPokemonColor(context, typeofpokemon.first())

                iv_pokemon.apply {
                    loadGlideImage(context, imageurl)
                    setBackgroundColor(color)
                }
                tv_pokemon.text = name

                tv_hp.apply {
                    text = hp.toString()
                    setTextColor(color)
                }

                card_pokemon.setOnClickListener { listener.clickPokemon(this) }
            }
        }

    }

    private fun inflateLayout(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
    override fun getItemCount() = items.size
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(items[position])
}