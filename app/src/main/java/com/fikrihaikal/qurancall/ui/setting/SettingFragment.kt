package com.fikrihaikal.qurancall.ui.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toProfile()
        toHome()
    }

    private fun toProfile() {
        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_profileFragment)
        }
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_homeFragment)
        }
    }


}