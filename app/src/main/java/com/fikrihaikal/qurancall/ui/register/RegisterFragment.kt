package com.fikrihaikal.qurancall.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentRegisterBinding
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

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
                            Log.d("memek", data?.error.toString())
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
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            Log.e("ERROR ANJING",it?.message!!)
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