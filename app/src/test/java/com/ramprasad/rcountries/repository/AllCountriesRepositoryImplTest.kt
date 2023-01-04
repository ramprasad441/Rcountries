package com.ramprasad.rcountries.repository

import com.ramprasad.rcountries.commons.ResponseState
import com.ramprasad.rcountries.model.Countries
import com.ramprasad.rcountries.network.RetrofitClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mockito
import retrofit2.Response

/**
 * Created by Ramprasad on 8/5/22.
 */
@ExperimentalCoroutinesApi
@RunWith(BlockJUnit4ClassRunner::class)
internal class AllCountriesRepositoryImplTest {

    private lateinit var mockRetrofitClient: RetrofitClient

    private lateinit var repo: AllCountriesRepositoryImpl

    @Before
    fun before() {
        mockRetrofitClient = Mockito.mock(RetrofitClient::class.java)
        repo = AllCountriesRepositoryImpl(mockRetrofitClient)
    }

    @Test
    fun testEmptyListData() = runTest {
        Mockito.`when`(mockRetrofitClient.getAllCountries()).thenAnswer {
            Response.success(emptyList<Countries>())
        }
        val res: List<ResponseState> = repo.getAllCountries().toList()

        assert(true)
        Assert.assertEquals(0, (res[1] as ResponseState.SUCCESS).countries.count())
    }

    @Test
    fun testTwoItemsListData() = runTest {
        Mockito.`when`(mockRetrofitClient.getAllCountries()).thenAnswer {
            Response.success(createCountry())
        }
        val res: List<ResponseState> = repo.getAllCountries().toList()

        assert(true)
        Assert.assertEquals(2, (res[1] as ResponseState.SUCCESS).countries.count())
    }

    @Test
    fun testErrorData() = runTest {
        Mockito.`when`(mockRetrofitClient.getAllCountries()).thenAnswer {
            ResponseState.ERROR(Throwable())
        }
        val res: List<ResponseState> = repo.getAllCountries().toList()

        assert(true)
        assert((res[1] is ResponseState.ERROR))
    }

    private fun createCountry(): MutableList<Countries> {
        return mutableListOf<Countries>().apply {
            add(Countries("El Aaiún", "EH", name = "Western Sahara"))
            add(Countries("Mata-Utu", "WF", name = "Wallis and Futuna"))
        }
    }
}
