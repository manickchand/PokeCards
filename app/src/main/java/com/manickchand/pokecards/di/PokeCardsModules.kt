package com.manickchand.pokecards.di

import com.manickchand.pokecards.repository.PokeCardsRemoteSource
import com.manickchand.pokecards.repository.PokeCardsRepositoryImpl
import com.manickchand.pokecards.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val pokeCardsModules = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/manickchand/319a727dbdfb67782f45a91237333c0e/raw/64d27fa8665787f3c174f7ae3e4b2a56c1ee92a4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(PokeCardsRemoteSource::class.java) }

    factory { PokeCardsRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
}