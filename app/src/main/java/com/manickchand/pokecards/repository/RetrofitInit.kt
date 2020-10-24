package com.manickchand.pokecards.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInit {
    fun getClient() = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/manickchand/319a727dbdfb67782f45a91237333c0e/raw/64d27fa8665787f3c174f7ae3e4b2a56c1ee92a4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}