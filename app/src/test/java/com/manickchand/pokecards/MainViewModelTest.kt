package com.manickchand.pokecards

import android.content.Context
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manickchand.pokecards.model.PokemonModel
import com.manickchand.pokecards.repository.PokeCardsRepositoryImpl
import com.manickchand.pokecards.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val pokeCardsRepositoryImpl: PokeCardsRepositoryImpl = mock()
    private lateinit var viewModel: MainViewModel
    private val contextMock: Context = mock()
    private val mockResources: Resources = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before(){
        whenever(contextMock.resources).thenReturn(mockResources)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = MainViewModel(pokeCardsRepositoryImpl)
    }

    @Test
    fun `Should return correct list size`() {

        runBlockingTest {
            val expected = stubPokemonList().size
            whenever(pokeCardsRepositoryImpl.getPokemons()).thenReturn(stubPokemonList())

            viewModel.fetchPokemons(contextMock)

            assertEquals(expected, viewModel.getPokemonLiveData().value?.size)
        }

    }

    @Test
    fun `Should return empty list when receive null list`(){

        runBlockingTest {
            whenever(pokeCardsRepositoryImpl.getPokemons()).thenReturn(null)
            viewModel.fetchPokemons(contextMock)

            assertEquals(emptyList<PokemonModel>(), viewModel.getPokemonLiveData().value)
        }
    }

    private fun stubPokemonList() = 1.rangeTo(5).map { stubPokemon() }

    private fun stubPokemon() = PokemonModel(
        name = "",
        xdescription = "",
        hp = 0,
        attack = 0,
        defense = 0,
        imageurl = "",
        color = -1,
        typeofpokemon = emptyList()
    )
}