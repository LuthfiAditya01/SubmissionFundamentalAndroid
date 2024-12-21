package com.dicoding.submission1funda.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.submission1funda.ARG_PARAM1
import com.dicoding.submission1funda.ARG_PARAM2
import com.dicoding.submission1funda.R
import com.dicoding.submission1funda.databinding.FragmentFavouriteEventBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteEvent.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteEvent : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentFavouriteEventBinding? = null
    private var binding get() = _binding!!
    private val

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_event, container, false)
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