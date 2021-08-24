package com.busem.data.common

import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {

        val response: Response<T>

        try {

            response = call.invoke()
            if (response.isSuccessful) return response.body()

        } catch (e: SocketTimeoutException) {
            throw DataException.SocketTimeoutException("Slow Network")
        } catch (e: Exception) {
            throw DataException.ApiException("Unknown Error - ${e.message.toString()}")
        }

        checkErrors(response.code(), response.message())
        return null
    }

    fun <T : Any> apiImageRequest(call: Call<T>): T? {

        val response: Response<T>

        try {

            response = call.execute()
            if (response.isSuccessful) return response.body()

        } catch (e: SocketTimeoutException) {
            throw DataException.SocketTimeoutException("Slow Network")
        } catch (e: Exception) {
            throw DataException.ApiException("Unknown Error - ${e.message.toString()}")
        }

        checkErrors(response.code(), response.message())
        return null
    }


    private fun checkErrors(code: Int, message: String) {
        when (code) {
            401 -> throw DataException.UnauthorizedException("Login Expired")
            403 -> throw DataException.ApiException("Resource Forbidden - $message")
            404 -> throw DataException.ApiException("Resource NotFound - $message")
            500 -> throw DataException.ApiException("Internal Server Error - $message")
            502 -> throw DataException.ApiException("Bad GateWay - $message")
            301 -> throw DataException.ApiException("Resource Removed - $message")
            302 -> throw DataException.ApiException("Removed Resource Found - $message")
            else -> throw DataException.ApiException("Unknown Error Code - $message")
        }
    }

}