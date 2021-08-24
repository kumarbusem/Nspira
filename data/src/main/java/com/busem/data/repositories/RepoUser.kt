package com.busem.data.repositories

import com.busem.data.local.dataSources.UserDataSource
import com.busem.data.local.dataSources.UserDataSourceImpl
import com.busem.data.models.User

class RepoUser : DataSourceUserRepo {

    private val dataSource: UserDataSource by lazy { UserDataSourceImpl() }

    override suspend fun saveUser(username: kotlin.String, password: kotlin.String) {
        dataSource.saveUser(username, password)
    }

    override suspend fun getUser(username: String, password: String): User? {
        return dataSource.getUser(username, password)
    }

    override suspend fun getUsers(): List<User> {
        return dataSource.getUsers()
    }

}