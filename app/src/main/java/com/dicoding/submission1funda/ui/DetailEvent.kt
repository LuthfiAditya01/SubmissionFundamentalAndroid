package com.dicoding.submission1funda.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.entity.DbDao
//import com.dicoding.submission1funda.data.response.ListEventsItem
import com.dicoding.submission1funda.databinding.ActivityMainBinding
import com.dicoding.submission1funda.databinding.FragmentDetailEventBinding
import com.dicoding.submission1funda.entity.DbRoomDatabase
import com.dicoding.submission1funda.entity.db
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class DetailEvent : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentDetailEventBinding? = null
    private lateinit var binding1: ActivityMainBinding
    private val binding get() = _binding!!
    private lateinit var dbDao: DbDao

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true) // Show loading indicator when view is created
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navView.visibility = View.GONE

        // Initialize DbDao
        val db = DbRoomDatabase.getDatabase(requireContext())
        dbDao = db.DbDao()

        val event = arguments?.getParcelable<Event>("event")
        event?.let {
            bindEventDetails(it)
            showLoading(false) // Hide loading indicator after data is bound
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentDetailEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun bindEventDetails(event: Event) {
        Glide.with(this)
            .load(event.imageLogo)
            .error(R.drawable.ic_error)
            .into(binding.imageEvent)

        binding.tvEventTitle.text = event.name
        binding.tvEventOwner.text = "Organizer: ${event.ownerName}"
        binding.tvEventTime.text = "Time: ${event.beginTime}"
        binding.tvEventQuota.text = "Quota: ${event.quota - event.registrants}"
        binding.tvEventDescription.text = HtmlCompat.fromHtml(event.description ?: "No description available", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.buttonEventLink.text = "Register"
        binding.buttonEventLink.isVisible = true
        binding.buttonEventLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)
        }
        binding.favouriteButton.setOnClickListener {
            Log.d("DetailEvent", "Favorite button clicked")
            Log.d("DetailEvent", "Current favorite status: ${event.isFavorite}")
            CoroutineScope(Dispatchers.IO).launch {
                if (event.isFavorite == null || event.isFavorite == false) {
                    binding.favouriteButton.setImageResource(R.drawable.ic_favourite)
                    dbDao.insertFavourite(db(event.id, event.name))
                    event.isFavorite = true
                    Log.d("DetailEvent", "Added to favorites: ${event.name}")
                } else if (event.isFavorite == true) {
                    binding.favouriteButton.setImageResource(R.drawable.ic_not_favourite)
                    dbDao.deleteFavourite(event.id)
                    event.isFavorite = false
                    Log.d("DetailEvent", "Removed from favorites: ${event.name}")
                }
            }
        }
        Log.d("DetailEvent", "Event Link: ${event.link}")
        Log.d("DetailEvent", "Button visibility: ${binding.buttonEventLink.visibility}")
    }

    override fun onDestroyView() {
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navView.visibility = View.VISIBLE
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}