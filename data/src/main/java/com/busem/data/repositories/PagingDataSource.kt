package com.busem.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busem.data.local.dao.GitHubDao
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Repository
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl
import java.lang.Exception

class PagingDataSource(
    private val searchKey: String,
    private val cache: GitHubDao = LocalGitDataSourceImpl(),
    private val remote: RemoteGitDataSource = RemoteGitDataSourceImpl()
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
       
            val currentLoadingPageKey = params.key ?: 1
            Log.e("PAGE::", currentLoadingPageKey.toString())
            val response = remote.fetchRepositories(searchKey, currentLoadingPageKey)
            Log.e("FIRST::", response?.repositories?.get(0).toString())
            val data = response?.repositories ?: emptyList()
            val repositoriesList = data.map { Repository.mapFromRemoteToLocal(it) }

            cache.clearRepos()
            cache.saveRepos(repositoriesList)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            return LoadResult.Page(
                data = cache.getRepos(),
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )

    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }
}