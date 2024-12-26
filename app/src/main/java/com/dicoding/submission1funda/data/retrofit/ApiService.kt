package com.dicoding.submission1funda.data.retrofit

import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.data.response.EventsResponse
import com.dicoding.submission1funda.data.response.EventResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Get details of a specific event by ID
    @GET("events/{id}")
    fun getDetailEvents(
        @Path("id") id: Int
    ): Call<EventResponse>

    // Get all active events (active=1)
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Int = 1
    ): Call<EventsResponse>

    // Get all completed events (active=0)
    @GET("events")
    fun getCompletedEvents(
        @Query("active") active: Int = 0
    ): Call<EventsResponse>

    // Search completed events based on a query
    @GET("events")
    fun searchCompletedEvents(
        @Query("active") active: Int = 0,
        @Query("query") query: String
    ): Call<EventsResponse>

    // Search active events based on a query
    @GET("events")
    fun searchActiveEvents(
        @Query("active") active: Int = 1,
        @Query("query") query: String
    ): Call<EventsResponse>

    // Get the nearest active event
    @GET("events/nearest")
    fun getNearestActiveEvent(): Call<EventsResponse>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): Response<Event>

    // Update this method to handle multiple IDs
    @GET("events")
    suspend fun getEventsByIds(
        @Query("ids") ids: List<Int>
    ): Response<EventsResponse>


}
