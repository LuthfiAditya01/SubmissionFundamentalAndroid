package com.dicoding.submission1funda.domain.repository;

import com.dicoding.submission1funda.data.response.EventsResponse;
import com.dicoding.submission1funda.data.response.EventResponse;
import retrofit2.Call;

interface MyRepository {
    fun getActiveEvents(): Call<EventsResponse>
    fun getCompletedEvents(): Call<EventsResponse>
    fun searchEventsCompleted(query: String): Call<EventsResponse>
    fun searchEventsActive(query: String): Call<EventsResponse>
    fun getDetailEvents(id: Int): Call<EventResponse>
    fun getNearestActiveEvent(): Call<EventsResponse>
}
