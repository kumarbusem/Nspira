package com.busem.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.busem.data.models.Repository


@Dao
interface GitHubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepos(repos: List<Repository>)

    @Query("SELECT * FROM repository WHERE id = :id")
    fun getRepo(id: Int): Repository?

    @Query("SELECT * FROM repository ORDER BY watchers_count DESC")
    fun getRepos(): List<Repository>

    @Query("DELETE FROM repository ")
    fun clearRepos()

//    @Query("SELECT * FROM repository ORDER BY watchers_count COLLATE NOCASE ASC")
//    fun getRepos(): PagingSource<Int, Repository>
}