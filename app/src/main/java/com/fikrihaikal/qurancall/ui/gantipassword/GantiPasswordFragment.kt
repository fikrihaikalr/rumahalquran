package com.fikrihaikal.qurancall.ui.gantipassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentGantiFotoBinding
import com.fikrihaikal.qurancall.databinding.FragmentGantiPasswordBinding
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

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

        val fullText = getString(R.string.rules_and_policies)
        val aturanText = getString(R.string.rules)
        val kebijakanText = getString(R.string.policies)
        val spannableString = createSpannableString(fullText,aturanText,kebijakanText)
        binding.tvAturanDanKebijakan.text = spannableString
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