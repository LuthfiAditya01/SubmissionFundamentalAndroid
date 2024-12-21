package com.dicoding.submission1funda.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1funda.databinding.FragmentHomeBinding
import com.dicoding.submission1funda.ui.EventsAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recycleViewEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val activeEventsAdapter = EventsAdapter(emptyList())
        binding.recycleViewEvent.adapter = activeEventsAdapter

        viewModel.activeEvents.observe(viewLifecycleOwner, Observer { activeEvents ->
            activeEvents?.let {
                activeEventsAdapter.updateData(it)
                showLoading(false)
            }
        })

        showLoading(true)
        // Fetch data
        viewModel.fetchActiveEvents()



//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarCompleted.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}