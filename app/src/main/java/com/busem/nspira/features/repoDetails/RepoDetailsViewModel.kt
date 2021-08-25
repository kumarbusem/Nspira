package com.busem.nspira.features.repoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.models.Contributor
import com.busem.data.models.Repository
import com.busem.data.repositories.DataSourceGithub
import com.busem.data.repositories.RepoGithub
import com.busem.nspira.common.BaseViewModel
import kotlinx.coroutines.launch


class RepoDetailsViewModel(
    private val githubRepo: DataSourceGithub = RepoGithub()
) : BaseViewModel() {

    val selectedRepo: MutableLiveData<Repository> = MutableLiveData()

    private val _contributors by lazy { MutableLiveData<List<Contributor>>() }
    val contributors: LiveData<List<Contributor>> by lazy { _contributors }

    init {
        getSelectedRepo()
        getContributors()
    }

    private fun getContributors() {
        if (!repoPrefs.getRepo()?.contributorsUrl.isNullOrEmpty()) {

            ioScope.launch {
                try {

                    _contributors.postValue(
                        githubRepo.fetchContributors(repoPrefs.getRepo()?.contributorsUrl!!)
                    )

                } catch (e: Exception) {
                    /**
                     * Here we will handle multiple exception types
                     * Unauthorized/Client/Server/Socket Timeout
                     */
                    e.printStackTrace()
                    obsToastMessage.postValue(e.message)
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
