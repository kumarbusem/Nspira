package com.busem.nspira.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.busem.data.local.dao.GitHubDao
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Repository
import com.busem.data.repositories.DataSourceGithub
import com.busem.data.repositories.PagingDataSource
import com.busem.data.repositories.RepoGithub
import com.busem.nspira.common.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repoGitHub: DataSourceGithub = RepoGithub(),
    private val cache: GitHubDao = LocalGitDataSourceImpl(),
) : BaseViewModel() {

    private val _obsPagingData by lazy { MutableLiveData<PagingData<Repository>>() }
    val obsPagingData: LiveData<PagingData<Repository>> by lazy { _obsPagingData }

    private val _repos by lazy { MutableLiveData<List<Repository>>() }
    val repos: LiveData<List<Repository>> by lazy { _repos }

    init {
        searchResults("Android")
    }


    fun searchResults(searchTerm: String) {
        try {
            ioScope.launch {

                Pager(
                    config = PagingConfig(pageSize = 15, enablePlaceholders = false)
                ) {
                    PagingDataSource(searchTerm)
                }.flow.cachedIn(ioScope).collect {

                    _obsPagingData.postValue(it)

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            obsToastMessage.postValue(e.message)
            _repos.postValue(repoGitHub.getRepositories())
        }
    }

    fun saveSelectedRepo(repo: Repository) {
        repoPrefs.saveRepo(repo)
    }
}