package com.fikrihaikal.qurancall.ui.auth.notifsuccess

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentNotifForgotPasswordBinding

class NotifForgotPasswordFragment : Fragment() {
    private var _binding: FragmentNotifForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotifForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotifForgotPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lottieNotif.playAnimation()
        binding.lottieNotif.repeatCount = 3
        toLogin()
    }

    private fun toLogin() {
        binding.btnKembaliKeMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_notifForgotPasswordFragment_to_loginFragment)
        }
    }


}