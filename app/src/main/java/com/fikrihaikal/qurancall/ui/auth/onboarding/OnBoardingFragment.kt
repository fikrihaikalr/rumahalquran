package com.fikrihaikal.qurancall.ui.auth.onboarding

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentOnBoardingBinding
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }
    lateinit var tokenPreferences: TokenPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToLogin()
        navigateToLoginWithSkip()
    }

    private fun navigateToLoginWithSkip() = with(binding){
        tvSkip.setOnClickListener {
            viewModel.saveOnBoarding("onBoarding")
            findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
    }

    private fun navigateToLogin() = with(binding){
        btnDone.setOnClickListener {
            viewModel.saveOnBoarding("onBoarding")
            findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
    }
}