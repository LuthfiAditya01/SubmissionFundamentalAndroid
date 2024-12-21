package com.dicoding.submission1funda.data.response

import com.google.gson.annotations.SerializedName

class EventResponse (

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("event")
    val event: Event
)