package com.ramprasad.rcountries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramprasad.rcountries.commons.ResponseState
import com.ramprasad.rcountries.repository.AllCountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ramprasad on 7/31/22.
 */
@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val allCountriesRepository: AllCountriesRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ParentViewModel() {

    private val _countries: MutableLiveData<ResponseState> =
        MutableLiveData(ResponseState.LOADING())
    val countries: LiveData<ResponseState> get() = _countries

    fun getListOfAllCountries() {
        viewModelSafeScope.launch(ioDispatcher) {
            allCountriesRepository.getAllCountries().collect {
                _countries.postValue(it)
            }
        }
    }
}
