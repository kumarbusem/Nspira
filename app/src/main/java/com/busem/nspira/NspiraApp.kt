package com.busem.nspira

import android.app.Application

class NspiraApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {

        lateinit var appContext: NspiraApp private set
    }
}