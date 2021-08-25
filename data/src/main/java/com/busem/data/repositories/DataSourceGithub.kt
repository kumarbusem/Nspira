package com.busem.data.repositories

import com.busem.data.common.DataState
import com.busem.data.models.Contributor
import com.busem.data.models.Repository

interface DataSourceGithub {


    suspend fun fetchRepositories(searchKey: String, page: Int): List<Repository>?

    suspend fun fetchContributors(url: String): List<Contributor>?

    fun getRepositories(): List<Repository>

}