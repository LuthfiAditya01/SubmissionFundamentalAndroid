package com.dicoding.submission1funda.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.data.retrofit.ApiConfig
import com.dicoding.submission1funda.entity.DbDao
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class FavouriteEventViewModel(private val dbDao: DbDao) : ViewModel() {
    private val _favouriteEvents = MutableLiveData<List<Event>>()
    val favouriteEvents: LiveData<List<Event>> = _favouriteEvents

    private val _isLoadingActive = MutableLiveData<Boolean>()
    val isLoadingActive: LiveData<Boolean> = _isLoadingActive

    fun fetchFavouriteEvents() {
        _isLoadingActive.value = true
        viewModelScope.launch {
            val favoriteIds = dbDao.getAllFavouriteIds()
            val events = fetchEventsFromApi() // Replace with your API call
            val favoriteEvents = events.map { event ->
                if (favoriteIds.contains(event.id)) {
                    event.isFavorite = true
                }
                event
            }
            _favouriteEvents.value = favoriteEvents
            _isLoadingActive.value = false
        }
    }

    private suspend fun fetchEventsFromApi(): List<Event> {
        return try {
            val response = ApiConfig.getApiService().getActiveEvents().awaitResponse()
            if (response.isSuccessful) {
                response.body()?.events ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}