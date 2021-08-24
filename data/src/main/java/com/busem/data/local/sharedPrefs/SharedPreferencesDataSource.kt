package com.busem.data.local.sharedPrefs

import com.busem.data.models.Repository


interface SharedPreferencesDataSource {

    abstract fun saveUrl(url: String)
    abstract fun getUrl(): String?
    abstract fun clearUrl()

    abstract fun saveRepo(repository: Repository)
    abstract fun getRepo(): Repository?
    abstract fun clearRepo()

    abstract fun deleteAllPrefs()
}