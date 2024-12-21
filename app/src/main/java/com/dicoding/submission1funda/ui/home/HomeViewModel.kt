package com.dicoding.submission1funda.ui.home

import android.util.Log
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

class HomeViewModel : ViewModel() {

    private val _activeEvents = MutableLiveData<List<Event>>()
    val activeEvents: LiveData<List<Event>> = _activeEvents

    // Indikator loading terpisah
    private val _isLoadingActive = MutableLiveData<Boolean>()
    val isLoadingActive: LiveData<Boolean> = _isLoadingActive

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    // Fungsi untuk memuat active events
    fun fetchActiveEvents() {
        _isLoadingActive.value = true
        val client = ApiConfig.getApiService().getActiveEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _activeEvents.value = response.body()?.events?.take(5) ?: listOf()
                } else {
                    Log.e("HomeViewModel", "Error: ${response.errorBody()?.string()}")
                    _activeEvents.value = listOf()
                }
                _isLoadingActive.value = false
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Failure: ${t.message}")
                _activeEvents.value = listOf()
                _isLoadingActive.value = false
            }
        })
    }

}