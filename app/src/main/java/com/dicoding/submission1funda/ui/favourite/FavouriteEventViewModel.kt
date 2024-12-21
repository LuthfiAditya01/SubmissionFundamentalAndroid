package com.dicoding.submission1funda.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.data.retrofit.ApiConfig
import com.dicoding.submission1funda.entity.DbDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteEventViewModel(private val dbDao: DbDao) : ViewModel() {

    private val _favouriteEvents = MutableLiveData<List<Event>>()
    val favouriteEvents: LiveData<List<Event>> = _favouriteEvents

    private val _isLoadingActive = MutableLiveData<Boolean>()
    val isLoadingActive: LiveData<Boolean> = _isLoadingActive

    fun fetchFavouriteEvents() {
        viewModelScope.launch {
            _isLoadingActive.value = true
            try {
                // Step 1: Ambil ID favorit dari database
                val favouriteIds = withContext(Dispatchers.IO) {
                    dbDao.getAllFavouriteIds()
                }

                // Step 2: Ambil detail acara dari API berdasarkan ID favorit
                if (favouriteIds.isNotEmpty()) {
                    val events = mutableListOf<Event>()
                    for (id in favouriteIds) {
                        val response = ApiConfig.getApiService().getEventById(id) // Ganti dengan API yang sesuai
                        if (response.isSuccessful) {
                            response.body()?.let { event ->
                                events.add(event)
                            }
                        } else {
                            Log.e("FavouriteEventViewModel", "Failed to fetch event with ID: $id")
                        }
                    }

                    // Step 3: Update LiveData dengan daftar acara favorit
                    _favouriteEvents.value = events
                } else {
                    _favouriteEvents.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("FavouriteEventViewModel", "Error: ${e.message}")
                _favouriteEvents.value = emptyList()
            } finally {
                _isLoadingActive.value = false
            }
        }
    }
}
