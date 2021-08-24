package com.busem.data.local.sharedPrefs

import com.busem.data.models.Repository

class SharedPreferencesDataSourceImpl(
    private val mSpHelper: SharedPreferencesHelper = SharedPreferencesHelper.getInstance()
) : SharedPreferencesDataSource {


    override fun saveUrl(url: String) = mSpHelper.putObject(SP_URL, url)
    override fun getUrl(): String? = mSpHelper.getObject(SP_URL)
    override fun clearUrl() =mSpHelper.remove(SP_URL)


    override fun saveRepo(repository: Repository) = mSpHelper.putObject(SP_SELECTED_REPO, repository)
    override fun getRepo(): Repository? = mSpHelper.getObject(SP_SELECTED_REPO)
    override fun clearRepo() = mSpHelper.remove(SP_SELECTED_REPO)


    override fun deleteAllPrefs() = mSpHelper.clear()


    companion object {
        private const val SP_URL: kotlin.String = "SP_URL"
        private const val SP_SELECTED_REPO: kotlin.String = "SP_SELECTED_REPO"
    }

}