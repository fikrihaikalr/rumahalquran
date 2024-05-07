package com.fikrihaikal.qurancall.ui.auth.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val tokenPreferences: TokenPreferences) : ViewModel() {

    val role = tokenPreferences.getUserRoles().asLiveData(Dispatchers.IO)
    val onBoarding = tokenPreferences.getOnBoarding().asLiveData(Dispatchers.IO)
}