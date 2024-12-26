package com.dicoding.submission1funda.ui.darkMode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.dicoding.submission1funda.ui.darkMode.DarkModeViewModel;

import javax.inject.Inject

class DarkModeViewModel @Inject constructor(
    private val pref: SettingPreferences
) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
