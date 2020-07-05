package com.bookstore.client.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bookstore.client.config.AppConfig
import com.bookstore.client.dao.local.LocalUserDAO
import com.bookstore.model.response.user.AccessToken
import org.koin.core.KoinComponent
import org.koin.core.inject

@Database(entities = [AccessToken::class], version = 1, exportSchema = false)
abstract class LocalDatabaseImpl: RoomDatabase() {

    abstract val userDAO : LocalUserDAO

    companion object: KoinComponent {
        private val context: Context by inject()

        val database = Room.databaseBuilder(
            context,
            LocalDatabaseImpl::class.java,
            AppConfig.ROOM_DEFAULT_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}