package com.manickchand.pokecards.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.manickchand.pokecards.R
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.utils.getPokemonColor
import com.manickchand.pokecards.utils.loadGlideImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailDialogFragment : DialogFragment() {

    private val pokemonModel by lazy { requireArguments().getSerializable(EXTRA_POKEMON) as PokemonModel }

    companion object {
        private const val EXTRA_POKEMON = "pokemon"

        @JvmStatic
        fun newInstance(pokemonModel: PokemonModel) =
            DetailDialogFragment().apply {
                arguments = bundleOf(EXTRA_POKEMON to pokemonModel)
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_detail, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewData()
    }

    private fun setViewData() {


        pokemonModel.run {
            val color = getPokemonColor(requireContext(), typeofpokemon.first())

            tv_name.text = name
            iv_pokemon.loadGlideImage(requireContext(), imageurl)
            tv_description.text = xdescription

            tv_type.apply {
                text = typeofpokemon.first()
                setTextColor(color)
            }

            tv_hp.apply {
                text = hp.toString()
                setTextColor(color)
            }

            tv_df.apply {
                text = defense.toString()
                setTextColor(color)
            }

            tv_at.apply {
                text = attack.toString()
                setTextColor(color)
            }

            card_detail_pokemon.setCardBackgroundColor(color)
        }
    }

}