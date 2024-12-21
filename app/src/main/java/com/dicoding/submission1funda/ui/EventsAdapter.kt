package com.dicoding.submission1funda.ui

import android.os.Bundle
import android.provider.CalendarContract
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.data.response.Event

//import com.dicoding.submission1funda.data.response.ListEventsItem

class EventsAdapter(private var events: List<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("event", event)
            }
            it.findNavController().navigate(R.id.action_completedEvent_to_eventDetailFragment, bundle)
        }
    }

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageLogo: ImageView = itemView.findViewById(R.id.img_item_photo)
        private val eventName: TextView = itemView.findViewById(R.id.event_name)
        private val eventSummary: TextView = itemView.findViewById(R.id.event_description)

        fun bind(event: Event) {
            Log.d("EventViewHolder", "Binding event: ${event.name}")
            eventName.text = event.name
            eventSummary.text = Html.fromHtml(event.summary, Html.FROM_HTML_MODE_LEGACY)
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .error(R.drawable.ic_error) // Placeholder for error
                .into(imageLogo)
        }
    }


}