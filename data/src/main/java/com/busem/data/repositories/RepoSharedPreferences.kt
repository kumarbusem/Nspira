package com.busem.data.repositories

import com.busem.data.local.sharedPrefs.SharedPreferencesDataSource
import com.busem.data.local.sharedPrefs.SharedPreferencesDataSourceImpl
import com.busem.data.models.Repository

class RepoSharedPreferences(
    private val mSpDS: SharedPreferencesDataSource = SharedPreferencesDataSourceImpl()
): SharedPreferencesDataSource {


    override fun saveUrl(url: String) = mSpDS.saveUrl(url)
    override fun getUrl(): String? = mSpDS.getUrl()
    override fun clearUrl() = mSpDS.clearUrl()


    override fun saveRepo(repository: Repository) = mSpDS.saveRepo(repository)
    override fun getRepo(): Repository? = mSpDS.getRepo()
    override fun clearRepo() = mSpDS.clearRepo()


    override fun deleteAllPrefs() = mSpDS.deleteAllPrefs()
}