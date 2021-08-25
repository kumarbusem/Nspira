package com.busem.data.remote

import com.busem.data.models.Contributor


interface RemoteGitDataSource {

    suspend fun fetchRepositories(searchKey: String, page: Int): RepositoriesResponseBody?

    suspend fun fetchContributors(url: String): List<Contributor>?

}