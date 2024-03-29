package com.fikrihaikal.qurancall.ui.login

import androidx.lifecycle.ViewModelProvider
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentLoginBinding
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.fikrihaikal.qurancall.utils.dataStore
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel : LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var tokenPreferences: TokenPreferences
    private lateinit var token : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toRegist()
        observeLogin()
        onBackPressed()
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    private fun observeLogin() {
        binding.btnLogin.setOnClickListener { btn ->
            val email = binding.edEmailLogin.text.toString()
            val password = binding.edPasswordLogin.text.toString()
            val dataStore: DataStore<Preferences> = requireContext().dataStore
            tokenPreferences = TokenPreferences.getInstance(dataStore)


            loginViewModel.login(LoginBody(email, password))
            loginViewModel.login.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            binding.pbLogin.isVisible = true
                        }
                        is Resource.Success ->{
                            binding.pbLogin.isVisible = false
                            val data = it.data
                            if (data?.error == true){
                                Toast.makeText(requireContext(),"Gagal Register",Toast.LENGTH_LONG).show()
                            }else{

                                data?.loginResult?.let { loginResult ->
                                    lifecycleScope.launch {
                                        loginResult.id?.let { it1 -> tokenPreferences.saveUserId(it1) }
                                        loginResult.token?.let { itToken -> tokenPreferences.saveToken(itToken) }
                                    }
                                }
                                Toast.makeText(requireContext(),"Success Login",Toast.LENGTH_LONG).show()
                                btn.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                        }
                        is Resource.Error ->{
                            binding.pbLogin.isVisible = false
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun toRegist() {
        binding.btnMasukDisini.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}