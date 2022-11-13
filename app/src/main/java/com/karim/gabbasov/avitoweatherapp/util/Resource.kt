package com.karim.gabbasov.avitoweatherapp.util

/**
 * Class to aid with receiving responses.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * Response is a success, containing [data].
     */
    class Success<T>(data: T?) : Resource<T>(data)
    /**
     * Response was a failure, with the [message] containing information on why.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
