package com.busem.nspira.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.busem.data.common.DataState
import com.busem.data.models.Repository
import com.busem.data.repositories.DataSourceGithub
import com.busem.data.repositories.RepoGithub
import com.busem.nspira.common.BaseViewModel
import com.busem.nspira.common.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(
    private val githubRepo: DataSourceGithub = RepoGithub()
) : BaseViewModel() {

    private val _searchTerm by lazy { SingleLiveEvent<String>() }

    private val _repos by lazy { MutableLiveData<List<Repository>>() }
    val repos: LiveData<List<Repository>> by lazy { _repos }

    init {
        searchResults("Android")
    }

    fun searchResults(searchTerm: String) {
        _searchTerm.postValue(searchTerm)

        ioScope.launch {
            doWhileLoading {
                when (val dataState = githubRepo.fetchRepositories(searchTerm)) {

                    is DataState.Success -> _repos.postValue(dataState.data)

                    is DataState.Error -> {

                        handleExceptions(dataState.dataException)
                        dataState.logDetails()
                        githubRepo.getRepositories()

                    }
                }
            }
        }
    }

    fun saveSelectedRepo(repo: Repository) {
        repoPrefs.saveRepo(repo)
    }


}