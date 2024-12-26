package com.dicoding.submission1funda.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.data.response.EventsResponse
import com.dicoding.submission1funda.data.retrofit.ApiConfig
import com.dicoding.submission1funda.entity.DbDao
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            val events = fetchEventsFromApi(favoriteIds) // Replace with your API call
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

    private suspend fun fetchEventsFromApi(ids: List<Int>): List<Event> {
        return try {
            if (ids.isEmpty()) {
                return emptyList()
            }

            val response = ApiConfig.getApiService().getEventsByIds(ids)
            if (response.isSuccessful) {
                response.body()?.events ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }



//    private suspend fun fetchEventsFromApi(id : List<Int>): List<Event> {
//        return try {
//            val response = ApiConfig.getApiService().getEventById(id).awaitResponse()
//            if (response.isSuccessful) {
//                response.body()?.events ?: emptyList()
//            } else {
//                emptyList()
//            }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
}