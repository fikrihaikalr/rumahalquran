package com.fikrihaikal.qurancall.ui.user.fotoprofile

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentGantiFotoBinding

class GantiFotoFragment : Fragment() {
    private val REQUEST_IMAGE_PICK = 1001
    private var _binding: FragmentGantiFotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGantiFotoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toProfile()

        val fullText = getString(R.string.rules_and_policies)
        val aturanText = getString(R.string.rules)
        val kebijakanText = getString(R.string.policies)
        val spannableString = createSpannableString(fullText,aturanText,kebijakanText)
        binding.tvAturanDanKebijakan.text = spannableString

        pickPhoto()
    }

    private fun pickPhoto() {
        binding.icCamera.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null){
            val selectedImageUri = data.data
            selectedImageUri?.let {
                binding.inputPhoto.setImageURI(it)
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


    private fun toProfile() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_gantiFotoFragment_to_profileFragment)
        }
    }
}