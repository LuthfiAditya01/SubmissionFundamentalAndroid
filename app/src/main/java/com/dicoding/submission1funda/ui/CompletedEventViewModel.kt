package com.dicoding.submission1funda.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.data.response.EventsResponse
//import com.dicoding.submission1funda.data.response.ListEventsItem
import com.dicoding.submission1funda.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletedEventViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events
//    val events: LiveData<List<Event>> = MutableLiveData()

    fun fetchCompletedEvents() {

        val client = ApiConfig.getApiService().getCompletedEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _events.value = response.body()?.events
                } else {
                    _events.value = emptyList()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                _events.value = emptyList()
            }
        })
    }
}