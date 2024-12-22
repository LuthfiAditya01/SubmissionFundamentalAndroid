package com.dicoding.submission1funda.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission1funda.entity.DbDao

class FavouriteEventViewModelFactory(private val dbDao: DbDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteEventViewModel(dbDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}