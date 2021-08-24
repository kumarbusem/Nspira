package com.busem.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busem.data.local.dataSources.LocalGitDataSource
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Repository
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl

class RepoPaging(
    private val key: String,
    private val cache: LocalGitDataSource = LocalGitDataSourceImpl(),
    private val remote: RemoteGitDataSource = RemoteGitDataSourceImpl()
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = remote.fetchRepositories(key, currentLoadingPageKey)
            val repositoriesList = mutableListOf<Repository>()

            val data = response?.repositories ?: emptyList()

            if (data.isNullOrEmpty()) {
                repositoriesList.addAll(cache.getRepositories())
            } else {
                repositoriesList.addAll(data.map {
                    Repository.mapFromRemoteToLocal(it)
                })
                if (currentLoadingPageKey == 1)
                    cache.saveRepositories(repositoriesList)
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            return LoadResult.Page(
                data = repositoriesList,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

}