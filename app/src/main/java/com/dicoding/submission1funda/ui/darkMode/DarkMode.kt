package com.dicoding.submission1funda.ui.darkMode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission1funda.databinding.FragmentDarkModeBinding

class DarkMode : Fragment() {

    private var _binding: FragmentDarkModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDarkModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referensi switch dari layout
        val switchTheme = binding.switchDarkMode

        // Inisialisasi SettingPreferences dan ViewModel
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val mainViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref))
            .get(DarkModeViewModel::class.java)

        // Observasi tema yang disimpan di data store
        mainViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            // Sinkronisasi status switch dengan mode saat ini
            switchTheme.setOnCheckedChangeListener(null) // Hindari loop perubahan
            switchTheme.isChecked = isDarkModeActive

            // Terapkan mode tema
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )

            // Aktifkan kembali listener
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked ->
                mainViewModel.saveThemeSetting(isChecked)
            }
        }

        // Listener untuk menyimpan perubahan tema
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
