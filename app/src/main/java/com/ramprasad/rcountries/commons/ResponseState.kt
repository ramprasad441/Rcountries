package com.ramprasad.rcountries.commons

import com.ramprasad.rcountries.model.Countries

/**
 * Created by Ramprasad on 7/31/22.
 */
sealed interface ResponseState {
    class ERROR(val error: Throwable) : ResponseState
    class LOADING(val isLoading: Boolean = true) : ResponseState
    class SUCCESS(val countries: List<Countries>) : ResponseState
}