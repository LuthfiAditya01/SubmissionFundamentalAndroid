package com.dicoding.submission1funda.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//@Entity
@Entity(tableName = "db")
@Parcelize
data class db(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,

    @ColumnInfo(name = "eventId")
    var eventId : Int = 0,

    @ColumnInfo(name = "eventName")
    var eventName : String? = null,

    @ColumnInfo(name = "isFavourite")
    var isFavourite : Boolean = false
) : Parcelable