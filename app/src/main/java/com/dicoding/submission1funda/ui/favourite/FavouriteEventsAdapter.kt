package com.dicoding.submission1funda.ui.favourite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.databinding.ItemEventBinding
import com.dicoding.submission1funda.ui.DetailEvent

class FavouriteEventsAdapter(
    private val onFavoriteChanged: (Event) -> Unit
) : ListAdapter<Event, FavouriteEventsAdapter.FavouriteEventViewHolder>(DIFF_CALLBACK) {

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
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("event", event)
            }
            it.findNavController().navigate(R.id.action_favouriteEvent_to_eventDetailFragment, bundle)
        }
    }

    inner class FavouriteEventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.apply {
                eventName.text = event.name
                eventDescription.text = event.description

                // Load image with placeholder and error handling
                Glide.with(imgItemPhoto.context)
                    .load(event.imageLogo)
                    .placeholder(R.drawable.placeholder_image) // Add a placeholder image
                    .error(R.drawable.ic_error) // Add an error image
                    .into(imgItemPhoto)

                // Handle favorite button click
                imgItemPhoto.setOnClickListener {
                    val updatedFavoriteStatus = !(event.isFavorite ?: false)
                    event.isFavorite = updatedFavoriteStatus
                    onFavoriteChanged(event)
                }

                // Navigate to DetailEventActivity on item click

            }
        }
    }
}