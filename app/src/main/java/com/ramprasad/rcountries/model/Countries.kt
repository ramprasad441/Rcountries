package com.ramprasad.rcountries.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ramprasad on 7/30/22.
 */
data class Countries(
    @SerializedName("capital") var capital: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("currency") var currency: Currency? = Currency(),
    @SerializedName("flag") var flag: String? = null,
    @SerializedName("language") var language: Language? = Language(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("region") var region: String? = null,
    var header: String? = null
)
