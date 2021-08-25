package com.busem.data.local.dataSources

import com.busem.data.local.dao.UserDao
import com.busem.data.local.roomDatabase.RoomProvider
import com.busem.data.models.User

class UserDataSourceImpl(
    private val cache: UserDao = RoomProvider.getInstance().userDao()
) : UserDao {

    override fun getUser(username: String, password: String): User? {
        return cache.getUser(username, password)
    }

    override fun getUsers(): List<User> {
        return cache.getUsers()
    }

    override fun clearUsers() {
        cache.clearUsers()
    }

    override fun saveUser(user: User) {
        cache.saveUser(user)
    }
}