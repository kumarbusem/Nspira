package com.busem.data.repositories

import com.busem.data.common.DataState
import com.busem.data.models.Contributor
import com.busem.data.models.Repository

interface DataSourceGithub {


    suspend fun fetchRepositories(searchKey: String): DataState<List<Repository>?>

    suspend fun fetchContributors(url: String): DataState<List<Contributor>?>

    fun getRepositories(): List<Repository>

}