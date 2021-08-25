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

    override suspend fun fetchRepositories(searchKey: String, page: Int): List<Repository> {

//        return try {
//
//            val repositoriesResponseBody = remote.fetchRepositories(searchKey, page)
//
//            if (repositoriesResponseBody != null) {
//                cache.saveRepos(repositoriesResponseBody.repositories.map {
//                    mapFromRemoteToLocal(it)
//                })
//            }
//
//            cache.getRepos()
//
//        } catch (e: Exception) {
//            throw e
//        }
        return emptyList()
    }


    override suspend fun fetchContributors(url: String):
            List<Contributor>? {
        return remote.fetchContributors(url)
    }


    override fun getRepositories(): List<Repository> = emptyList()
//        cache.getRepos()

}