package com.busem.data.repositories

import com.busem.data.common.DataState
import com.busem.data.common.EMPTY_STRING
import com.busem.data.local.dataSources.LocalGitDataSource
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.RemoteRepository
import com.busem.data.models.Repository
import com.busem.data.models.Repository.Companion.mapFromRemoteToLocal
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl

class RepoGithub : DataSourceGithub {

    private val cache: LocalGitDataSource by lazy { LocalGitDataSourceImpl() }
    private val remote: RemoteGitDataSource by lazy { RemoteGitDataSourceImpl() }

    override suspend fun fetchRepositories(searchKey: String):
            DataState<List<Repository>?> = DataState.asDataState {

        val repositoriesResponseBody = remote.fetchRepositories(searchKey)

        if (repositoriesResponseBody != null) {
            cache.saveRepositories(repositoriesResponseBody.repositories.map { mapFromRemoteToLocal(it) })
        }

        cache.getRepositories(searchKey)
    }


    override fun getRepositories(searchKey: String): List<Repository> =
        cache.getRepositories(searchKey)

    override fun saveRepository(repo: Repository) =
        cache.saveRepository(repo)

}