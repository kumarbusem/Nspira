package com.busem.data.repositories

import com.busem.data.common.DataState
import com.busem.data.local.dataSources.LocalGitDataSource
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Contributor
import com.busem.data.models.Repository
import com.busem.data.models.Repository.Companion.mapFromRemoteToLocal
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl

class RepoGithub(

    private val cache: LocalGitDataSource = LocalGitDataSourceImpl(),
    private val remote: RemoteGitDataSource = RemoteGitDataSourceImpl()

) : DataSourceGithub {

    override suspend fun fetchRepositories(searchKey: String):
            DataState<List<Repository>?> = DataState.asDataState {

        val repositoriesResponseBody = remote.fetchRepositories(searchKey)

        if (repositoriesResponseBody != null) {
            cache.saveRepositories(repositoriesResponseBody.repositories.map {
                mapFromRemoteToLocal(
                    it
                )
            })
        }

        cache.getRepositories()
    }


    override suspend fun fetchContributors(url: String):
            DataState<List<Contributor>?> = DataState.asDataState {
        remote.fetchContributors(url)
    }


    override fun getRepositories(): List<Repository> =
        cache.getRepositories()

}