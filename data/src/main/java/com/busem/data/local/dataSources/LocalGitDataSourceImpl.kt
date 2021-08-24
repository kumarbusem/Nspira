package com.busem.data.local.dataSources

import com.busem.data.local.roomDatabase.RoomProvider
import com.busem.data.models.Repository

class LocalGitDataSourceImpl : LocalGitDataSource {

    private val cache = RoomProvider.getInstance().githubDao()

    override fun saveRepositories(repositories: List<Repository>) {
        cache.saveRepos(repositories)
    }

    override fun getRepositories(): List<Repository> {
        return cache.getRepos()
    }

    override fun getRepository(id: Int): Repository? {
        return cache.getRepo(id)
    }

}