package com.busem.nspira.features.repoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.common.DataException
import com.busem.data.common.DataState
import com.busem.data.local.sharedPrefs.SharedPreferencesDataSource
import com.busem.data.models.Contributor
import com.busem.data.models.Repository
import com.busem.data.repositories.*
import com.busem.nspira.common.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RepoDetailsViewModel(
    private val githubRepo: DataSourceGithub = RepoGithub()
)  : BaseViewModel() {

    val selectedRepo: MutableLiveData<Repository> = MutableLiveData()

    private val _contributors by lazy { MutableLiveData<List<Contributor>>() }
    val contributors: LiveData<List<Contributor>> by lazy { _contributors }

    init {
        getSelectedRepo()
        getContributors()
    }

    private fun getContributors() {
        if(!repoPrefs.getRepo()?.contributorsUrl.isNullOrEmpty()){

            ioScope.launch {
                withContext(ioScope.coroutineContext) {
                    githubRepo.fetchContributors(repoPrefs.getRepo()?.contributorsUrl!!)
                }?.let { repos ->
                    _contributors.postValue(repos)
                    return@let
                } ?: run {
                    return@run
                }
            }

        }
    }

    private fun getSelectedRepo() {
        selectedRepo.postValue(repoPrefs.getRepo())
    }

    fun saveURL(link: String?) {
        repoPrefs.saveUrl(link!!)
    }

    fun saveRepoURL() {
        repoPrefs.saveUrl(selectedRepo.value?.htmlUrl!!)
    }

}
