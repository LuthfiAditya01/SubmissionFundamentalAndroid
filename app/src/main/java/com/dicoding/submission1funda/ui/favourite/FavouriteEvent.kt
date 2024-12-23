package com.dicoding.submission1funda.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1funda.databinding.FragmentFavouriteEventBinding
import com.dicoding.submission1funda.entity.DbRoomDatabase

class FavouriteEvent : Fragment() {
    private var _binding: FragmentFavouriteEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewAdapter = FavouriteEventsAdapter(
            onFavoriteChanged = { event ->
                // Handle favorite status change here
            }
        )

        binding.recycleViewFavouriteEvent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        val viewModel: FavouriteEventViewModel by viewModels {
            FavouriteEventViewModelFactory(DbRoomDatabase.getDatabase(requireContext()).DbDao())
        }

        viewModel.favouriteEvents.observe(viewLifecycleOwner) { events ->
            recyclerViewAdapter.submitList(events)
        }

        viewModel.isLoadingActive.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarCompleted.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.fetchFavouriteEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}