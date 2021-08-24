package com.busem.data.remote

import com.busem.data.common.SafeApiRequest
import com.busem.data.models.Contributor

class RemoteGitDataSourceImpl : RemoteGitDataSource, SafeApiRequest() {

    private val service = ServiceProvider.getInstance().create(GithubService::class.java)

    override suspend fun fetchRepositories(searchKey: String, page: Int): RepositoriesResponseBody? {
        return apiRequest { service.fetchRepositories(searchKey, 1, 10) }
    }

    override suspend fun fetchContributors(url: String): List<Contributor>? {
        return apiRequest { service.fetchContributors(url) }
    }

}