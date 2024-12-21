package com.dicoding.submission1funda.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DbDao {

    // Insert the event into the database. If it already exists (same ID), replace the existing entry.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favoriteEvent: db)

    // Delete the event from the favorites table
    @Query("DELETE FROM favorite_event WHERE id = :eventId")
    suspend fun deleteFavourite(eventId: Int)

    // Check if the event exists in the favorites table
    @Query("SELECT * FROM favorite_event WHERE id = :eventId")
    suspend fun getEventById(eventId: Int): db?

//    @Query("SELECT * FROM favorite_event WHERE isFavorite = 1")
//    suspend fun getFavourite()
    @Query("SELECT id FROM favorite_event")
    suspend fun getAllFavouriteIds(): List<Int>

}
