package com.dicoding.submission1funda.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {
    @Insert
    fun insertFavourite(favoriteEvent: db)

    @Query("DELETE FROM favorite_event WHERE id = :eventId")
    fun deleteFavourite(eventId: Int)
}
