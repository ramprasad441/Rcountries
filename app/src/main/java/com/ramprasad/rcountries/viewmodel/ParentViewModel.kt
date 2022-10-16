package com.ramprasad.rcountries.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

/**
 * Created by Ramprasad on 7/31/22.
 */

private const val TAG = "ParentViewModel"

open class ParentViewModel : ViewModel() {

    protected val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, exception.localizedMessage, exception)
        }
    }
}
