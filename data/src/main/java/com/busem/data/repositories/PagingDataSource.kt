package com.busem.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busem.data.common.IS_CACHE_ALREADY_LOADED
import com.busem.data.local.dao.GitHubDao
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Repository

class PagingDataSource(
    private val searchKey: String,
    private val repoGitHub: DataSourceGithub = RepoGithub(),
    private val cache: GitHubDao = LocalGitDataSourceImpl(),
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {

        val position = params.key ?: 1

        return try {

            /**
             * Fetching data from network client and cond converting remote objects list to
             * local objects list
             */
            val list = repoGitHub.fetchRepositories(searchKey, position) ?: emptyList()

            Log.i("DATA::", "Page: $position,  List:${list.size}")

            /**
             * Deleting all repositories in cache and
             * Saving recent 15 repositories in Local Cache
             */
            cache.clearRepos()
            cache.saveRepos(list)
            /**
             * on network success returning LoadResult with remote list
             */
            LoadResult.Page(
                data = list,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )

        } catch (e: Exception) {

            val list = cache.getRepos()

            /**
             * According to task we are storing only recent 15 items cache
             * So If there is any network issues or exceptions while network requests
             * We are returning the items stored in the cache
             * Before returning cache data we are checking cache is available and
             * We have already returned the cache or not
             */
            return if (list.isNullOrEmpty() || IS_CACHE_ALREADY_LOADED) {

                LoadResult.Error(e)

            } else {

                IS_CACHE_ALREADY_LOADED = true
                LoadResult.Page(
                    data = list,
                    prevKey = null,
                    nextKey = position + 1
                )

            }
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        TODO("Not yet implemented")
    }

}