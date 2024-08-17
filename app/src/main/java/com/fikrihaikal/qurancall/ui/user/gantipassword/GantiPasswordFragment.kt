package com.fikrihaikal.qurancall.ui.user.gantipassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentGantiFotoBinding
import com.fikrihaikal.qurancall.databinding.FragmentGantiPasswordBinding
import com.fikrihaikal.qurancall.domain.model.user.resetpassword.UserResetPassword
import com.fikrihaikal.qurancall.domain.usecase.resetpassword.ValidationResultResetPassword
import com.fikrihaikal.qurancall.network.model.response.resetpassword.ResetPasswordBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import kotlinx.coroutines.launch

class GantiPasswordFragment : Fragment() {
    private var _binding: FragmentGantiPasswordBinding? = null
    private val binding get() = _binding!!

    private val editProfileViewModel: GantiPasswordViewModel by viewModels{
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGantiPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfileViewModel.getUser()
        toProfile()
        observeUser()
        observeResetPassword()
        setupUi()
        val fullText = getString(R.string.rules_and_policies)
        val aturanText = getString(R.string.rules)
        val kebijakanText = getString(R.string.policies)
        val spannableString = createSpannableString(fullText,aturanText,kebijakanText)
        binding.tvAturanDanKebijakan.text = spannableString
    }

    private fun setupUi() {
        setupTextWatchers()
        observeViewModel()
    }
    private fun observeViewModel(){
        editProfileViewModel.validationResultResetPassword.observe(viewLifecycleOwner, Observer { result ->
            displayValidationResult(result)
        })
    }
    private fun displayValidationResult(result: ValidationResultResetPassword){
        if (result.isValid){
            clearErrors()
            binding.btnSimpan.isEnabled = true
        }else {
            binding.btnSimpan.isEnabled = false
            displayErrors(result.errors)
        }
    }

    private fun clearErrors(){
        binding.passwordLamaLayout.error = null
        binding.passwordBaruLayout.error = null
        binding.konfirmasiPasswordBaruLayout.error = null
    }

    private fun displayErrors(errors: List<String>){
        clearErrors()
        errors.forEach{ error ->
            when{
                error.contains("lama") -> binding.passwordLamaLayout.error = error
                error.contains("Password") && !error.contains("Konfirmasi") -> binding.passwordBaruLayout.error = error
                error.contains("Konfirmasi") -> binding.konfirmasiPasswordBaruLayout.error = error
            }
        }
    }

    private fun setupTextWatchers(){
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateForm()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.edPasswordLamaProfile.addTextChangedListener(textWatcher)
        binding.edPasswordBaruProfile.addTextChangedListener(textWatcher)
        binding.edKonfirmasiPasswordBaruProfile.addTextChangedListener(textWatcher)
    }

    private fun validateForm(){
        val user = UserResetPassword(
            oldPassword = binding.edPasswordLamaProfile.text.toString(),
            newPassword = binding.edPasswordBaruProfile.text.toString(),
            confirmNewPassword = binding.edKonfirmasiPasswordBaruProfile.text.toString()
        )
        editProfileViewModel.validateResetPassword(user)
    }

    private fun observeResetPassword() {
        binding.btnSimpan.setOnClickListener {
            val oldPassword = binding.edPasswordLamaProfile.text.toString()
            val newPassword = binding.edPasswordBaruProfile.text.toString()
            editProfileViewModel.resetPassword(ResetPasswordBody(oldPassword,newPassword))
            editProfileViewModel.updatePassword.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            binding.progressBar.isVisible = true
                        }
                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            val data = it.data
                            if (data?.error == true){
                                Toast.makeText(requireContext(),"Gagal Update Password",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(requireContext(),"Berhasil update password",Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.homeFragment)
                            }
                        }
                        is Resource.Error ->{
                            binding.progressBar.isVisible = false
                            editProfileViewModel.clearState()
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


    private fun createSpannableString(fullText: String, aturanText: String, kebijakanText: String): SpannableString {
        val spannableString = SpannableString(fullText)
        val startIndexAturan = fullText.indexOf(aturanText)
        val endIndexAturan = startIndexAturan + aturanText.length
        val startIndexKebijakan = fullText.indexOf(kebijakanText)
        val endIndexKebijakan = startIndexKebijakan + kebijakanText.length

        val colorAturan = ContextCompat.getColor(requireContext(),R.color.biru_500)
        val colorKebijakan = ContextCompat.getColor(requireContext(),R.color.biru_500)

        addColorSpan(spannableString, startIndexAturan,endIndexAturan,colorAturan)
        addColorSpan(spannableString,startIndexKebijakan,endIndexKebijakan,colorKebijakan)

        return spannableString
    }

    private fun addColorSpan(spannableString: SpannableString, startIndex: Int, endIndex: Int, color: Int) {
        spannableString.setSpan(ForegroundColorSpan(color),startIndex,endIndex,0)
    }

    private fun observeUser() = with(binding){
        editProfileViewModel.editProfile.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Success ->{
                    val email = resources.data?.email
                    val emailEditTable = email?.let { Editable.Factory.getInstance().newEditable(it) }
                    binding.edEmailProfile.text = emailEditTable
                }
                is Resource.Error ->{
                    Toast.makeText(requireContext(),"Error: ${resources.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading ->{}
            }
        }
    }

    private fun toProfile() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_gantiPasswordFragment_to_profileFragment)
        }
    }
}