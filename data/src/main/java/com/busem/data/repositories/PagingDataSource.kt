package com.busem.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busem.data.local.dao.GitHubDao
import com.busem.data.local.dataSources.LocalGitDataSourceImpl
import com.busem.data.models.Repository
import com.busem.data.remote.RemoteGitDataSource
import com.busem.data.remote.RemoteGitDataSourceImpl
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class PagingDataSource(
    private val searchKey: String,
    private val repoGitHub: DataSourceGithub = RepoGithub(),
    private val remote: RemoteGitDataSource = RemoteGitDataSourceImpl(),
    private val cache: GitHubDao = LocalGitDataSourceImpl(),
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return try {

            val position = params.key ?: 1

            val list = remote.fetchRepositories(searchKey, position)?.repositories?.map {
                Repository.mapFromRemoteToLocal(it)
            } ?: emptyList()

            Log.e("DATA::", "Page: $position,  List:${list.size}")

            cache.saveRepos(list)
//            return cache.getRepos().load(params)
            LoadResult.Page(
                data = list,
                prevKey = if (position == 1) null else position-1,
                nextKey = if (list.isEmpty()) null else position+1
            )

        } catch (e: IOException){
            LoadResult.Error(e)
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        TODO("Not yet implemented")
    }

}