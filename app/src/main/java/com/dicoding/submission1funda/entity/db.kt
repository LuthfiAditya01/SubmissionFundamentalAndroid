package com.dicoding.submission1funda.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event")
data class db(
    @PrimaryKey val id: Int
)