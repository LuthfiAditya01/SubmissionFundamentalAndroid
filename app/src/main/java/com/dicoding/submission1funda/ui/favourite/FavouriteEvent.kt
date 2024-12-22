package com.dicoding.submission1funda.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.databinding.FragmentFavouriteEventBinding
import com.dicoding.submission1funda.entity.DbRoomDatabase

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteEvent.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        val recyclerViewAdapter = FavouriteEventAdapter() // Adapter untuk RecyclerView
        binding.recyclerView.adapter = recyclerViewAdapter

        val viewModel: FavouriteEventViewModel by viewModels {
            FavouriteEventViewModelFactory(DbRoomDatabase.getDatabase(requireContext()).DbDao())
        }

        // Observe daftar acara favorit
        viewModel.favouriteEvents.observe(viewLifecycleOwner) { events ->
            recyclerViewAdapter.submitList(events) // Perbarui RecyclerView dengan data
        }

        // Observe status loading
        viewModel.isLoadingActive.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Panggil fetchFavouriteEvents untuk memuat data
        viewModel.fetchFavouriteEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouriteEvent.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteEvent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}