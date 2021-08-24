package com.busem.nspira

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class NspiraApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {

        lateinit var appContext: NspiraApp private set
    }
}