package com.busem.data.common

import retrofit2.Call
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T? {

        val response: Response<T>

        try {

            response = call.invoke()
            if (response.isSuccessful) return response.body()

        } catch (e: SocketTimeoutException) {
            throw SocketTimeoutException("Slow Network")
        } catch (e: Exception) {
            throw ClientException("Unknown Error - ${e.message.toString()}")
        }

        checkErrors(response.code(), response.message())
    }

    fun <T : Any> apiImageRequest(call: Call<T>): T? {

        val response: Response<T>

        try {

            response = call.execute()
            if (response.isSuccessful) return response.body()

        } catch (e: SocketTimeoutException) {
            throw SocketTimeoutException("Slow Network")
        } catch (e: Exception) {
            throw ClientException("Unknown Error - ${e.message.toString()}")
        }

        checkErrors(response.code(), response.message())
    }


    private fun checkErrors(code: Int, message: String): Nothing {
        when (code) {
            400 -> throw ClientException("Bad Request")
            401 -> throw UnauthorizedException("Login Expired")
            403 -> throw ClientException("Resource Forbidden - $message")
            404 -> throw ClientException("Resource NotFound - $message")
            500 -> throw ServerException("Internal Server Error - $message")
            502 -> throw ServerException("Bad GateWay - $message")
            301 -> throw ServerException("Resource Removed - $message")
            302 -> throw ServerException("Removed Resource Found - $message")
            else -> throw ClientException("Unknown Error Code - $message")
        }
    }

}