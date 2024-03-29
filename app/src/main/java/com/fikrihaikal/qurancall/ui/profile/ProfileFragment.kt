package com.fikrihaikal.qurancall.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentProfileBinding
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.fikrihaikal.qurancall.utils.dataStore
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var tokenPreferences: TokenPreferences

    private val profileView: ProfileViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var idUser: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPreferences()
        logout()
    }

    private fun initPreferences() {
        val dataStore: DataStore<Preferences> = requireContext().dataStore
        tokenPreferences = TokenPreferences.getInstance(dataStore)
        lifecycleScope.launch {
            idUser = tokenPreferences.getUserId().toString()
            observeUser()
        }
    }

    private fun observeUser() = with(binding) {
        lifecycleScope.launch {
            if (idUser != null){
                profileView.getUser(idUser.toInt())

                profileView.profile.observe(viewLifecycleOwner){resources ->
                    when(resources){
                        is Resource.Success ->{
                            val username = resources.data?.username
                            val email = resources.data?.email
                            val usernameEditTable = username?.let{Editable.Factory.getInstance().newEditable(it)}
                            val emailEditTabele = email?.let { Editable.Factory.getInstance().newEditable(it) }
                            binding.tvNameProfile.text = username
                            binding.edUsernameProfile.text = usernameEditTable
                            binding.edEmailProfile.text = emailEditTabele
                        }
                        is Resource.Error ->{
                            Toast.makeText(requireContext(),"Error: ${resources.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading ->{}
                    }
                }
            }
        }
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
            tokenPreferences.deleteId()
        }
    }


}