package com.fikrihaikal.qurancall.ui.teacher.setting

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentSettingTeacherBinding

class SettingTeacherFragment : Fragment() {
    private var _binding: FragmentSettingTeacherBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SettingTeacherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toHome()
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingTeacherFragment_to_homeTeacherFragment)
        }
    }
}