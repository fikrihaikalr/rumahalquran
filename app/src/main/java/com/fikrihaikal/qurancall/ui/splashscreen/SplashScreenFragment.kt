package com.fikrihaikal.qurancall.ui.splashscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentSplashScreenBinding
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.fragment.findNavController

class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    lateinit var tokenPreferences: TokenPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashDelay()
    }

    private fun splashDelay() {
        val time = 2000L
        Handler().postDelayed({
            setNavigationToken()
        },time)
    }

    private fun setNavigationToken() {
        val dataStore : DataStore<Preferences> = requireContext().dataStore
        tokenPreferences = TokenPreferences.getInstance(dataStore)

        val token = TokenPreferences.getTokenSynchronously(dataStore)
        if (token != null){
            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }else{
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}