package com.dicoding.submission1funda.data.repository

import retrofit2.Call
import com.dicoding.submission1funda.data.response.EventsResponse
import com.dicoding.submission1funda.data.response.EventResponse
import com.dicoding.submission1funda.data.retrofit.ApiService
import com.dicoding.submission1funda.domain.repository.MyRepository

class MyRepositoryImpl(
    private val apiService: ApiService
) : MyRepository {
    override fun getActiveEvents(): Call<EventsResponse> {
        return apiService.getActiveEvents()
    }

    override fun getCompletedEvents(): Call<EventsResponse> {
        return apiService.getCompletedEvents()
    }

    override fun searchEventsCompleted(query: String): Call<EventsResponse> {
        return apiService.searchCompletedEvents(active = 0, query = query)
    }

    override fun searchEventsActive(query: String): Call<EventsResponse> {
        return apiService.searchActiveEvents(active = 1, query = query)
    }

    override fun getDetailEvents(id: Int): Call<EventResponse> {
        return apiService.getDetailEvents(id)
    }

    override fun getNearestActiveEvent(): Call<EventsResponse> {
        return apiService.getNearestActiveEvent()
    }

}
