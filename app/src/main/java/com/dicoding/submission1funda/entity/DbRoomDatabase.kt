package com.dicoding.submission1funda.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [db::class], version = 2)
abstract class DbRoomDatabase : RoomDatabase() {
    abstract fun DbDao(): DbDao

    companion object {
        @Volatile
        private var INSTANCE: DbRoomDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create a new table without the eventName column
                database.execSQL("""
                    CREATE TABLE new_favorite_event (
                        id INTEGER NOT NULL PRIMARY KEY
                    )
                """.trimIndent())

                // Copy the data from the old table to the new table
                database.execSQL("""
                    INSERT INTO new_favorite_event (id)
                    SELECT id FROM favorite_event
                """.trimIndent())

                // Remove the old table
                database.execSQL("DROP TABLE favorite_event")

                // Rename the new table to the old table name
                database.execSQL("ALTER TABLE new_favorite_event RENAME TO favorite_event")
            }
        }

        @JvmStatic
        fun getDatabase(context: Context): DbRoomDatabase {
            if (INSTANCE == null) {
                synchronized(DbRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DbRoomDatabase::class.java, "favorite_event_database"
                    )
                        .addMigrations(MIGRATION_1_2) // Add the migration here
                        .build()
                }
            }
            return INSTANCE as DbRoomDatabase
        }
    }
}