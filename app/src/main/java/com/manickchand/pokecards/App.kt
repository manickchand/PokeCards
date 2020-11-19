package com.manickchand.pokecards

import android.app.Application
import com.manickchand.pokecards.di.pokeCardsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(pokeCardsModules)
        }
    }
}