package com.fikrihaikal.qurancall.ui.auth.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnBoardingViewModel(private val tokenPreferences: TokenPreferences) : ViewModel() {
//    val onBoard = tokenPreferences.getOnBoarding().asLiveData(Dispatchers.IO)

    fun saveOnBoarding(onBoarding:String) {
        viewModelScope.launch (Dispatchers.IO){
            tokenPreferences.saveOnBoarding(onBoarding)
        }
    }
}