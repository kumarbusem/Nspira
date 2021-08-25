package com.busem.data.local.dataSources

import com.busem.data.local.dao.GitHubDao
import com.busem.data.local.roomDatabase.RoomProvider
import com.busem.data.models.Repository

class LocalGitDataSourceImpl(
    private val cache: GitHubDao = RoomProvider.getInstance().githubDao()
) : GitHubDao {

    override fun saveRepos(repos: List<Repository>) {
        cache.saveRepos(repos)
    }

    override fun getRepo(id: Int): Repository? {
        return cache.getRepo(id)
    }

    override fun getRepos(): List<Repository> {
        return cache.getRepos()
    }

    override fun clearRepos() {
        cache.clearRepos()
    }

}