package com.fikrihaikal.qurancall.ui.teacher.profileteacher

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fikrihaikal.qurancall.MainActivity
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentProfileTeacherBinding
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class ProfileTeacherFragment : Fragment() {
    private var _binding: FragmentProfileTeacherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileTeacherViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        logout()
        observeUser()
        toChangePassword()
        toChangePhoto()
    }

    private fun toChangePassword() {
        binding.constraintPassword.setOnClickListener {
            findNavController().navigate(R.id.action_profileTeacherFragment_to_gantiPasswordTeacherFragment)
        }
    }
    private fun toChangePhoto() {
        binding.cv1.setOnClickListener{
            findNavController().navigate(R.id.action_profileTeacherFragment_to_gantiFotoTeacherFragment)
        }
    }

    private fun observeUser() = with(binding) {
        viewModel.profile.observe(viewLifecycleOwner) { resources ->
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
            viewModel.deleteIdNDeleteToken()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
    }
}