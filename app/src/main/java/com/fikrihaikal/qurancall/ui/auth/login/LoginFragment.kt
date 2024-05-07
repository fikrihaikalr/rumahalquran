package com.fikrihaikal.qurancall.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentLoginBinding
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.ui.teacher.TeacherActivity
import com.fikrihaikal.qurancall.ui.user.UserActivity
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel : LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

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
                                    //check roles
                                    val roles = loginResult.roles
                                    if (roles != null && roles.contains("ROLE_TEACHER")){
                                        //user is teacher
                                        loginResult.id?.let { it1 ->
                                            loginResult.token?.let { it2 ->
                                                loginResult.roles?.let { it3 ->
                                                loginViewModel.saveIdNSaveToken(
                                                    it2,
                                                    it1
                                                )
                                                    loginViewModel.saveUserRole(it3)
                                                }
                                            }
                                        }
                                        Toast.makeText(requireContext(),"Success Login sebagai Guru",Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(requireContext(),TeacherActivity::class.java))
                                        activity?.finish()
                                    }else if (roles != null && roles.contains("ROLE_STUDENTS")){
                                        loginResult.id?.let { it1 ->
                                            loginResult.token?.let { it2 ->
                                                loginResult.roles?.let { it3 ->

                                                loginViewModel.saveIdNSaveToken(
                                                    it2,
                                                    it1
                                                )
                                                    loginViewModel.saveUserRole(it3)
                                                }
                                            }
                                        }
                                        Toast.makeText(requireContext(), "Success Login Sebagai User", Toast.LENGTH_SHORT)
                                            .show()
                                        startActivity(Intent(requireContext(),UserActivity::class.java))
                                        activity?.finish()
                                    }else{
                                        Toast.makeText(requireContext(),"Role not recognized",Toast.LENGTH_SHORT).show()
                                    }
//                                    loginResult.id?.let { it1 ->
//                                        loginResult.token?.let { it2 ->
//                                            loginViewModel.saveIdNSaveToken(
//                                                it2,
//                                                it1
//                                            )
//                                        }
//                                    }
                                }
//                                Toast.makeText(requireContext(),"Success Login",Toast.LENGTH_LONG).show()
//                                btn.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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