package com.ramprasad.rcountries.repository

import com.ramprasad.rcountries.commons.FailureResponse
import com.ramprasad.rcountries.commons.NullResponseMessage
import com.ramprasad.rcountries.commons.ResponseState
import com.ramprasad.rcountries.model.Countries
import com.ramprasad.rcountries.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ramprasad on 7/31/22.
 */

class AllCountriesRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient
) : AllCountriesRepository {

    override fun getAllCountries(): Flow<ResponseState> =
        flow {
            emit(ResponseState.LOADING())

            try {
                val response = retrofitClient.getAllCountries()
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        // val sortedList  = newDataSet.sortedBy { it.name }
                        val mapOfCharAndData = it.groupBy {
                            it.name?.get(0)?.uppercaseChar()
                        }
                        val headerList = mapOfCharAndData.keys.map {
                            Countries(header = it.toString())
                        }
                        val resultList = headerList + it
                        val sortedList = resultList.sortedBy {
                            if (it.header != null) {
                                it.header
                            } else {
                                it.name
                            }
                        }
                        emit(ResponseState.SUCCESS(sortedList))
                    } ?: throw NullResponseMessage()
                } else {
                    throw FailureResponse()
                }
            } catch (e: Exception) {
                emit(ResponseState.ERROR(e))
            }
        }
}

interface AllCountriesRepository {
    fun getAllCountries(): Flow<ResponseState>
}
