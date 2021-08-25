package com.busem.data.repositories

import com.busem.data.local.dao.UserDao
import com.busem.data.local.dataSources.UserDataSourceImpl
import com.busem.data.models.User

class RepoUser(
    private val dataSource: UserDao = UserDataSourceImpl()
) : DataSourceUserRepo {


    override suspend fun saveUser(username: String, password: String) {
        dataSource.saveUser(User(username, password))
    }

    override suspend fun getUser(username: String, password: String): User? {
        return dataSource.getUser(username, password)
    }

    override suspend fun getUsers(): List<User> {
        return dataSource.getUsers()
    }

    override suspend fun clearUsers() {
        dataSource.clearUsers()
    }

}