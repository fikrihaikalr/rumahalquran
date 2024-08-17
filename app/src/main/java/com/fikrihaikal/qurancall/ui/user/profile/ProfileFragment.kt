package com.fikrihaikal.qurancall.ui.user.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fikrihaikal.qurancall.MainActivity
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

    private val profileView: ProfileViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileView.getUser()
        toChangePhoto()
        toChangePassword()
        logout()
        observeUser()
    }


    private fun toChangePassword() {
        binding.constraintPassword.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_gantiPasswordFragment)
        }
    }

    private fun toChangePhoto() {
        binding.cv1.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_uploadFotoFragment)
        }
    }


    private fun observeUser() = with(binding) {
        profileView.profile.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Success -> {
                    Glide.with(requireContext()).load(resources.data?.photoPath)
                        .into(binding.ivProfile)
                    val username = resources.data?.username
                    val email = resources.data?.email
                    val usernameEditTable =
                        username?.let { Editable.Factory.getInstance().newEditable(it) }
                    val emailEditTable =
                        email?.let { Editable.Factory.getInstance().newEditable(it) }
                    binding.tvNameProfile.text = username
                    binding.edUsernameProfile.text = usernameEditTable
                    binding.edEmailProfile.text = emailEditTable
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(), "Error: ${resources.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {}
            }
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            profileView.deleteIdNDeleteToken()
            startActivity(Intent(requireContext(),MainActivity::class.java))
            activity?.finish()
        }
    }


}