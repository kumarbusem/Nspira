package com.busem.data.repositories

import com.busem.data.local.dao.GitHubDao
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Contributor
import com.busem.data.models.Repository
import com.busem.data.models.Repository.Companion.mapFromRemoteToLocal
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl

class RepoGithub(
    private val cache: GitHubDao = LocalGitDataSourceImpl(),
    private val remote: RemoteGitDataSource = RemoteGitDataSourceImpl()
) : DataSourceGithub {

    override suspend fun fetchRepositories(searchKey: String): List<Repository> {

        val repositoriesResponseBody = remote.fetchRepositories(searchKey, 1)

        if (repositoriesResponseBody != null) {
            cache.saveRepos(repositoriesResponseBody.repositories.map {
                mapFromRemoteToLocal(it)
            })
        }

        return cache.getRepos()
    }


    override suspend fun fetchContributors(url: String):
            List<Contributor>? {
        return remote.fetchContributors(url)
    }


    override fun getRepositories(): List<Repository> =
        cache.getRepos()

}