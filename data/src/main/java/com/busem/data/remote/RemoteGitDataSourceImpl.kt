package com.busem.data.remote

import com.busem.data.common.SafeApiRequest
import com.busem.data.models.Contributor

class RemoteGitDataSourceImpl : RemoteGitDataSource, SafeApiRequest() {

    private val service = NetworkClient.getInstance().create(NetworkInterface::class.java)

    override suspend fun fetchRepositories(
        searchKey: String,
        page: Int
    ): RepositoriesResponseBody? {
        return apiRequest { service.fetchRepositories(searchKey, page, 15) }
    }

    override suspend fun fetchContributors(url: String): List<Contributor>? {
        return apiRequest { service.fetchContributors(url) }
    }

}