package com.ramprasad.rcountries.commons

/**
 * Created by Ramprasad on 7/31/22.
 */
class NullResponseMessage(message: String = "The Response is null") : Exception(message)
class FailureResponse(message: String = "Error: Failed to fetch the response") : Exception(message)
