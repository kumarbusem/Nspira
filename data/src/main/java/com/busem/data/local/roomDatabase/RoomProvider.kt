package com.busem.data.local.roomDatabase

import android.content.Context
import androidx.room.Room

/**
 * Singleton to instantiate and provide the local cache handling object.
 */
object RoomProvider {

    private const val DATABASE_NAME = "busem_db"

    private var setup: RoomLocalDatabase? = null

    fun initialize(context: Context) {
        if (setup == null) {
            setup = Room.databaseBuilder(
                context,
                RoomLocalDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

    fun getInstance(): RoomLocalDatabase {
        checkNotNull(setup) { "GithubDatabase not initialized" }
        return setup!!
    }

}