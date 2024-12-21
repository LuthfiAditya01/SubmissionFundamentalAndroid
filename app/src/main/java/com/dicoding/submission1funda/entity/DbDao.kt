package com.dicoding.submission1funda.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteEvent(favoriteEvent: db)

    @Update
    fun updateFavoriteEvent(favoriteEvent: db)

    @Delete
    fun deleteFavoriteEvent(favoriteEvent: db)

    @Query("DELETE FROM db WHERE id = :eventId")
    fun deleteFavoriteEventById(eventId: Int)

    @Query("SELECT * FROM db")
    fun getAllFavoriteEvents(): LiveData<List<db>>

    @Query("SELECT * FROM db WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<db>

    @Query("SELECT * FROM db WHERE id = :id LIMIT 1")
    suspend fun getFavoriteEventByIdSync(id: Int): db?

    @Query("SELECT * FROM db WHERE eventName LIKE '%' || :query || '%'")
    fun searchFavoriteEvents(query: String): LiveData<List<db>>

    @Query("SELECT EXISTS (SELECT 1 FROM db WHERE id = :eventId)")
    suspend fun isFavorite(eventId: Int): Boolean
}