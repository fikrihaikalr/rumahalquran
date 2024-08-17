package com.fikrihaikal.qurancall.ui.auth.forgotpassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentForgotPasswordBinding
import com.fikrihaikal.qurancall.network.model.response.forgotpassword.ForgotPasswordBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForgotPasswordViewModel by viewModels{
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fullText = getString(R.string.periksa_email_anda_setelah_mengirim_email_untuk_nmendapatkan_tautan_untuk_mengatur_ulang_npassword_anda)
        val emailText = getString(R.string.email)
        val mengaturUlangText = getString(R.string.mengatur_ulang)
        val passwordText = getString(R.string.password)
        val spannableString = createSpannableString(fullText,emailText,mengaturUlangText,passwordText)
        binding.tvAturanDanKebijakan.text = spannableString
        toLogin()
        observeForgotPassword()
    }



    private fun observeForgotPassword() {
        binding.btnSimpan.setOnClickListener {
            val email = binding.edEmailForgotPassword.text.toString()
            viewModel.forgotPassword(ForgotPasswordBody(email))
            viewModel.forgotPasswordResponse.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            binding.pbForgotPassword.isVisible = true
                        }
                        is Resource.Success ->{
                            viewModel.clearState()
                            binding.pbForgotPassword.isVisible = false
                            val data = it.data
                            if (data?.error == true){
                                Toast.makeText(requireContext(),"Gagal Reset Password",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(requireContext(),"Berhasil Kirim Email",Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_forgotPasswordFragment_to_notifForgotPasswordFragment)
                            }
                        }
                        is Resource.Error ->{
                            viewModel.clearState()
                            binding.pbForgotPassword.isVisible = false
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun toLogin() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
    }

    private fun createSpannableString(
        fullText: String,
        emailText: String,
        mengaturUlangText: String,
        passwordText: String
    ): SpannableString {
        val spannableString = SpannableString(fullText)
        val startIndexEmail = fullText.indexOf(emailText)
        val endIndexEmail = startIndexEmail + emailText.length
        val startIndexMengaturUlang = fullText.indexOf(mengaturUlangText)
        val endIndexMengaturUlang = startIndexMengaturUlang + mengaturUlangText.length
        val startIndexPassword = fullText.indexOf(passwordText)
        val endIndexPassword = startIndexPassword + passwordText.length

        val colorEmail = ContextCompat.getColor(requireContext(),R.color.biru_500)
        val colorMengaturUlang = ContextCompat.getColor(requireContext(),R.color.biru_500)
        val colorPassword = ContextCompat.getColor(requireContext(),R.color.biru_500)

        addColorSpan(spannableString,startIndexEmail,endIndexEmail,colorEmail)
        addColorSpan(spannableString,startIndexMengaturUlang,endIndexMengaturUlang,colorMengaturUlang)
        addColorSpan(spannableString,startIndexPassword,endIndexPassword,colorPassword)

        return spannableString
    }

    private fun addColorSpan(
        spannableString: SpannableString,
        startIndex: Int,
        endIndexEmail: Int,
        color: Int
    ) {
        spannableString.setSpan(ForegroundColorSpan(color),startIndex,endIndexEmail,0)
    }
}