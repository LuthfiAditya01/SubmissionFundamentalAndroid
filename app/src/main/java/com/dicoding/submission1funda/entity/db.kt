package com.dicoding.submission1funda.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_event")
data class db(
    @PrimaryKey val id: Int,
    val eventName: String
)