package com.ramprasad.rcountries.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 7/30/22.
 */
data class Language(
    @SerializedName("code") var code: String? = null,
    @SerializedName("name") var name: String? = null
)
