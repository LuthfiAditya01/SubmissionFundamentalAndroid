package com.dicoding.submission1funda.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.databinding.FragmentCompletedEventBinding

class CompletedEvent : Fragment() {

    private var _binding: FragmentCompletedEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CompletedEventViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EventsAdapter(emptyList())

        binding.recycleViewCompletedEvent.adapter = adapter
        binding.recycleViewCompletedEvent.layoutManager = LinearLayoutManager(context)

        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                adapter.updateData(it)
                showLoading(false) // Hide loading indicator after data is fetched
            }
        })

        showLoading(true) // Show loading indicator before starting data fetch
        viewModel.fetchCompletedEvents()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarCompleted.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}