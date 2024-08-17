package com.fikrihaikal.qurancall.ui.auth.splashscreen

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.ui.teacher.TeacherActivity
import com.fikrihaikal.qurancall.ui.user.UserActivity
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.fikrihaikal.qurancall.utils.isJwtExpired
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    lateinit var tokenPreferences: TokenPreferences
    private val viewModel: SplashScreenViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashDelay()
//        checkOnBoarding()
    }

    private fun splashDelay() {
        val time = 2000L
        Handler().postDelayed({
            checkOnBoarding()
        }, time)
    }

    private fun checkOnBoarding() {
        viewModel.onBoarding.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
            } else {
                setNavigationToken()
            }
        }
    }

    private fun setNavigationToken() {
        val dataStore: DataStore<Preferences> = requireContext().dataStore
        tokenPreferences = TokenPreferences.getInstance(dataStore)
        val token = TokenPreferences.getTokenSynchronously(dataStore)
        if (token != null) {
            viewModel.role.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                } else {
                    if (token.isJwtExpired()) {
                        viewModel.removeSession()
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                    } else {
                        when {
                            it.contains("ROLE_TEACHER") -> {
                                startActivity(Intent(requireContext(), TeacherActivity::class.java))
                            }

                            it.contains("ROLE_STUDENTS") -> {
                                startActivity(Intent(requireContext(), UserActivity::class.java))
                            }

                            else -> {
                                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                            }
                        }
                        requireActivity().finish()
                    }
                }
            }
        } else {
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

//    private fun setNavigationToken() {
//        val dataStore : DataStore<Preferences> = requireContext().dataStore
//        tokenPreferences = TokenPreferences.getInstance(dataStore)
//
//        val token = TokenPreferences.getTokenSynchronously(dataStore)
//        if (token != null){
//            startActivity(Intent(requireContext(),UserActivity::class.java))
//            activity?.finish()
//        }else{
//            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
//        }
//    }