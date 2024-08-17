package com.fikrihaikal.qurancall.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentRegisterBinding
import com.fikrihaikal.qurancall.domain.model.user.register.User
import com.fikrihaikal.qurancall.domain.usecase.register.ValidationResult
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toLogin()
        observeRegist()
        setupUi()

    }

    private fun setupUi() {
        setupTextWatchers()
        observeViewModel()

    }

    private fun observeViewModel() {
        registViewModel.validationResult.observe(viewLifecycleOwner, Observer { result ->
            displayValidationResult(result)
        })
    }
    private fun displayValidationResult(result: ValidationResult) {
        if (result.isValid) {
            clearErrors()
            binding.btnRegister.isEnabled = true
        } else {
            binding.btnRegister.isEnabled = false
            displayErrors(result.errors)
        }
    }
    private fun clearErrors() {
        binding.usernameLayout.error = null
        binding.emailLayout.error = null
        binding.passwordLayout.error = null
        binding.repeatPasswordLayout.error = null
    }
    private fun displayErrors(errors: List<String>) {
        clearErrors()
        errors.forEach { error ->
            when {
                error.contains("Username") -> binding.usernameLayout.error = error
                error.contains("Email") -> binding.emailLayout.error = error
                error.contains("Password") && !error.contains("Konfirmasi") -> binding.passwordLayout.error = error
                error.contains("Konfirmasi") -> binding.repeatPasswordLayout.error = error
            }
        }
    }
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateForm()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.edUsernameRegister.addTextChangedListener(textWatcher)
        binding.edEmailRegister.addTextChangedListener(textWatcher)
        binding.edPasswordRegister.addTextChangedListener(textWatcher)
        binding.edConfirmPasswordRegister.addTextChangedListener(textWatcher)
    }
    private fun validateForm() {
        val user = User(
            username = binding.edUsernameRegister.text.toString(),
            email = binding.edEmailRegister.text.toString(),
            password = binding.edPasswordRegister.text.toString(),
            confirmPassword = binding.edConfirmPasswordRegister.text.toString()
        )
        registViewModel.validateUser(user)
    }

    private fun observeRegist() {
        binding.btnRegister.setOnClickListener { btn ->
            val username = binding.edUsernameRegister.text.toString()
            val email = binding.edEmailRegister.text.toString()
            val password = binding.edPasswordRegister.text.toString()
            registViewModel.register(RegisterBody(username, email, password))
            registViewModel.register.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            showLoading(true)
                        }
                        is Resource.Success ->{
                            showLoading(false)
                            val data = it.data
                            Log.d("register observe", data?.error.toString())
                            if (data != null){
                                if (data.error == true){
                                    Toast.makeText(requireContext(),"Gagal Register",Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(requireContext(),"Register berhasil, Silahkan Login", Toast.LENGTH_LONG).show()
                                    btn.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                }
                            }
                        }
                        is Resource.Error ->{
                            showLoading(false)
                            Log.e("ERROR",it?.message!!)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(status:Boolean){
        if (status){
            binding.pbRegister.visibility = View.VISIBLE
        }else{
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }

    private fun toLogin() {
        binding.btnMasukDisini.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}