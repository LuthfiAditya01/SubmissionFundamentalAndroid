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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.data.response.Event
import com.dicoding.submission1funda.entity.DbDao
import com.dicoding.submission1funda.entity.db
import com.dicoding.submission1funda.entity.DbRoomDatabase
//import com.dicoding.submission1funda.data.response.ListEventsItem
import com.dicoding.submission1funda.databinding.ActivityMainBinding
import com.dicoding.submission1funda.databinding.FragmentDetailEventBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class DetailEvent : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentDetailEventBinding? = null
    private lateinit var binding1: ActivityMainBinding
    private val binding get() = _binding!!
    private lateinit var dbDao: DbDao
//    val database = MyDatabase.getDatabase(requireContext()) // Ganti `MyDatabase` dengan nama database Anda
//    dbDao = database.dbDao()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true) // Show loading indicator when view is created
        binding1 = ActivityMainBinding.inflate(layoutInflater)
        val navView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
        navView.visibility = View.GONE
        val database = DbRoomDatabase.getDatabase(requireContext()) // Ganti dengan nama database Anda
//        dbDao = database.DbDao() // Menentukan dbDao
        dbDao = DbRoomDatabase.getDatabase(requireContext()).DbDao()
//        val database = Room.databaseBuilder(
//            requireContext(),
//            MyDatabase::class.java,
//            "my_database" // Ganti nama database sesuai dengan implementasi Anda
//        ).build()
//        dbDao = database.dbDao()

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
//        binding.description.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        binding.tvEventDescription.text = HtmlCompat.fromHtml(event.description ?: "No description available", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.buttonEventLink.text = "Register"
        binding.buttonEventLink.isVisible = true
        binding.buttonEventLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)
        }
        binding.favouriteButton.setOnClickListener {
            lifecycleScope.launch {
                var isFavorite = event.isFavorite ?: false
                if (!isFavorite) {
                    withContext(Dispatchers.IO) {
                        dbDao.insertFavourite(event.id)
                        Log.d("DetailEvent", "Event with ID ${event.id} marked as favorite.")
                    }
                    isFavorite = true
                    binding.favouriteButton.setImageResource(R.drawable.ic_favourite)
                } else {
                    withContext(Dispatchers.IO) {
                        dbDao.deleteFavourite(event.id)
                        Log.d("DetailEvent", "Event with ID ${event.id} removed from favorites.")
                    }
                    isFavorite = false
                    binding.favouriteButton.setImageResource(R.drawable.ic_not_favourite)
                }

                // Optionally refresh UI state after updating database
                val updatedEvent = dbDao.getFavoriteEventByIdSync(event.id) // Assuming this method is available
                updatedEvent?.let {
                    binding.favouriteButton.setImageResource(
                        if (it.isFavourite) R.drawable.ic_favourite else R.drawable.ic_not_favourite
                    )
                }
            }
        }

        Log.d("DetailEvent", "Event Link: ${event.link}")
        Log.d("DetailEvent", "Button visibility: ${binding.buttonEventLink.visibility}")
        // Button Visibility: 0

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