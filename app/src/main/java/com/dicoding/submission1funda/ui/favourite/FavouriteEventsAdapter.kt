package com.dicoding.submission1funda.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.databinding.ItemEventBinding

class FavouriteEventsAdapter :
    ListAdapter<Event, FavouriteEventsAdapter.FavouriteEventViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteEventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavouriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class FavouriteEventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.eventName.text = event.name // Display event name
            binding.eventDescription.text = event.description // Display event description

            // Load image using Glide
            Glide.with(binding.imgItemPhoto.context)
                .load(event.imageLogo) // Replace with the correct image URL property
                .into(binding.imgItemPhoto)

            // Handle favorite button click
            binding.imgItemPhoto.setOnClickListener {
                val updatedFavoriteStatus = !(event.isFavorite ?: false)
                event.isFavorite = updatedFavoriteStatus
            }

            // Navigate to detail view on item click
            binding.root.setOnClickListener {
                onEventClicked?.invoke(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteEventViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavouriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}
