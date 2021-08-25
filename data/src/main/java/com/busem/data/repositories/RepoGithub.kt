package com.busem.data.repositories

import com.busem.data.models.Contributor
import com.busem.data.models.Repository
import com.busem.data.models.Repository.Companion.mapFromRemoteToLocal
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl

class RepoGithub(
    private val remote: RemoteGitDataSource = RemoteGitDataSourceImpl()
) : DataSourceGithub {

    override suspend fun fetchRepositories(searchKey: String, page: Int): List<Repository>? {
        val repositoriesResponseBody = remote.fetchRepositories(searchKey, 1)

        return repositoriesResponseBody?.repositories?.map {
            mapFromRemoteToLocal(it)
        }

    }

    override suspend fun fetchContributors(url: String): List<Contributor>? =
        remote.fetchContributors(url)

}