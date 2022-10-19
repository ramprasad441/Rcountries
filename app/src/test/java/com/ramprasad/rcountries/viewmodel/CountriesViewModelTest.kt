package com.ramprasad.rcountries.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ramprasad.rcountries.commons.ResponseState
import com.ramprasad.rcountries.repository.AllCountriesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ramprasad on 7/31/22.
 */

@ExperimentalCoroutinesApi
class CountriesViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = mockk<AllCountriesRepository>(relaxed = true)

    private lateinit var targetTest: CountriesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        targetTest = CountriesViewModel(mockRepository, testDispatcher)
    }

    @After
    fun shutdown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `getting countries when the server retrieves a list of countries when it returns error`() {
        every { mockRepository.getAllCountries() } returns flowOf(
            ResponseState.ERROR(Throwable("Error"))
        )
        val stateList = mutableListOf<ResponseState>()
        targetTest.countries.observeForever {
            stateList.add(it)
        }

        targetTest.getListOfAllCountries()

        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(ResponseState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(ResponseState.ERROR::class.java)

        assertThat((stateList[1] as ResponseState.ERROR).error.localizedMessage).isEqualTo("Error")
    }

    @Test
    fun `getting the countries when the server retrieves a list of countries when it returns loading`() {
        every { mockRepository.getAllCountries() } returns flowOf(
            ResponseState.LOADING()
        )
        val stateList = mutableListOf<ResponseState>()
        targetTest.countries.observeForever {
            stateList.add(it)
        }

        targetTest.getListOfAllCountries()

        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(ResponseState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(ResponseState.LOADING::class.java)

        assertThat((stateList[1] as ResponseState.LOADING).isLoading).isTrue()
    }

    @Test
    fun `getting the countries when the server retrieves the list of countries when it returns success`() {
        every { mockRepository.getAllCountries() } returns flowOf(
            ResponseState.SUCCESS(listOf(mockk()))
        )
        val stateList = mutableListOf<ResponseState>()
        targetTest.countries.observeForever {
            stateList.add(it)
        }

        targetTest.getListOfAllCountries()

        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(ResponseState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(ResponseState.SUCCESS::class.java)

        assertThat((stateList[1] as ResponseState.SUCCESS).countries).hasSize(1)
    }
}
