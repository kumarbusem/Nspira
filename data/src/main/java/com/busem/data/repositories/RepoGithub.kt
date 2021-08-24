package com.busem.data.repositories

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

    override suspend fun fetchRepositories(searchKey: String): List<Repository> {

        val repositoriesResponseBody = remote.fetchRepositories(searchKey, 1)

        if (repositoriesResponseBody != null) {
            cache.saveRepositories(repositoriesResponseBody.repositories.map {
                mapFromRemoteToLocal(it)
            })
        }

        return cache.getRepositories()
    }


    override suspend fun fetchContributors(url: String):
            List<Contributor>? {
        return remote.fetchContributors(url)
    }


    override fun getRepositories(): List<Repository> =
        cache.getRepositories()

}