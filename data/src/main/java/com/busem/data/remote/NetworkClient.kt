package com.busem.data.remote

import com.busem.data.common.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton remote service provider that provides the network call handling object.
 */
object NetworkClient {

    /**
     * Builds and provides the [Retrofit] object lazily.
     *
     * This object is required to make remote service calls and fetch the required
     * data from the internet.
     */

    private var setup: Retrofit? = null

    fun initialize() {
        if (setup == null) {
            setup = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
        }
    }

    fun getInstance(): Retrofit {
        checkNotNull(setup) { "SharedPreferences not initialized" }
        return setup!!
    }


}