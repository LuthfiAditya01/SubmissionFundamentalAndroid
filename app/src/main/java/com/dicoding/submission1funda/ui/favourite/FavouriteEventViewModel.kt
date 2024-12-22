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
                // Ambil semua ID favorit dari database
                val favouriteIds = withContext(Dispatchers.IO) {
                    dbDao.getAllFavouriteIds()
                }

                // Jika ada ID favorit, ambil data lengkap dari API
                if (favouriteIds.isNotEmpty()) {
                    val events = favouriteIds.mapNotNull { id ->
                        try {
                            val response = ApiConfig.getApiService().getEventById(id) // Panggil API
                            if (response.isSuccessful) {
                                response.body() // Ambil data event jika berhasil
                            } else {
                                Log.e("FavouriteEventViewModel", "Failed to fetch event with ID: $id")
                                null
                            }
                        } catch (e: Exception) {
                            Log.e("FavouriteEventViewModel", "Error fetching event ID $id: ${e.message}")
                            null
                        }
                    }

                    // Update LiveData dengan daftar event yang berhasil diambil
                    _favouriteEvents.value = events
                } else {
                    _favouriteEvents.value = emptyList() // Tidak ada ID favorit
                }
            } catch (e: Exception) {
                Log.e("FavouriteEventViewModel", "Error fetching favourite events: ${e.message}")
                _favouriteEvents.value = emptyList()
            } finally {
                _isLoadingActive.value = false
            }
        }
    }

}
