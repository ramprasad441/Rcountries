package com.ramprasad.rcountries

import androidx.annotation.VisibleForTesting
import com.ramprasad.rcountries.repository.AllCountriesRepository

/**
 * Created by Ramprasad on 7/31/22.
 */
object Injection {

    @Volatile
    var allCountriesRepository: AllCountriesRepository? = null
        @VisibleForTesting set
}