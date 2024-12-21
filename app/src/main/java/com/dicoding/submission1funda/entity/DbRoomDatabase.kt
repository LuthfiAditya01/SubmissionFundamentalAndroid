package com.dicoding.submission1funda.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [db::class], version = 1)
abstract class DbRoomDatabase : RoomDatabase() {
    abstract fun DbDao(): DbDao

    companion object {
        @Volatile
        private var INSTANCE: DbRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): DbRoomDatabase {
            if (INSTANCE == null) {
                synchronized(DbRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DbRoomDatabase::class.java, "favorite_event_database"
                    ).build()
                }
            }
            return INSTANCE as DbRoomDatabase
        }
    }
}