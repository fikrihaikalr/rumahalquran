package com.fikrihaikal.qurancall.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentProfileBinding
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.dataStore
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var tokenPreferences: TokenPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            deleteToken()
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun deleteToken() {
        val dataStore: DataStore<Preferences> = requireContext().dataStore
        tokenPreferences = TokenPreferences.getInstance(dataStore)
        lifecycleScope.launch {
            tokenPreferences.deleteToken()
        }
    }

}