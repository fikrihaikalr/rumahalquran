package com.fikrihaikal.qurancall.ui.user.fotoprofile.uploadfoto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentUploadFotoBinding
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class UploadFotoFragment : Fragment() {
    private var _binding: FragmentUploadFotoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UploadFotoViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }
    private var selectedPhotoFile: File? = null
    private val requestPhotoLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { handleSelectedPhoto(it) }
    }

    private fun handleSelectedPhoto(uri: Uri) {
        val file = File(requireContext().cacheDir, "temp_photo.jpg")
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        selectedPhotoFile = file
        binding.inputPhoto.setImageURI(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadFotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fullText = getString(R.string.rules_and_policies)
        val aturanText = getString(R.string.rules)
        val kebijakanText = getString(R.string.policies)
        val spannableString = createSpannableString(fullText, aturanText, kebijakanText)
        binding.tvAturanDanKebijakan.text = spannableString
        viewModel.getUser()
        toProfile()
        pickPhoto()
        observeUser()
        observeUpload()
    }

    private fun observeUser() = with(binding) {
        viewModel.userResponse.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("photoData", resource.data?.photoPath.toString())
                    Glide.with(requireContext()).load(resource.data?.photoPath)
                        .into(binding.inputPhoto)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                }
            }
        }

    }

    private fun pickPhoto() {
        binding.icCamera.setOnClickListener {
            requestPhotoLauncher.launch("image/*")
        }
    }

    private fun observeUpload() {
        binding.btnSimpan.setOnClickListener {
            observeUploadPhoto()
        }
    }

    private fun observeUploadPhoto() {
        selectedPhotoFile?.let { file ->
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData(
                name = "file",
                filename = file.name,
                body = requestFile
            )
            viewModel.uploadPhoto(body)
        }
        observeUploadPhotoResponse()
    }

    private fun observeUploadPhotoResponse() {
        viewModel.uploadPhotoResponse.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    val data = resource.data
                    if (data?.error == true) {
                        Toast.makeText(
                            requireContext(),
                            "Gagal Upload Photo",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Berhasil Upload Photo",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.homeFragment)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        "ERROR",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun toProfile() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_uploadFotoFragment_to_profileFragment)
        }
    }

    private fun createSpannableString(
        fullText: String,
        aturanText: String,
        kebijakanText: String
    ): SpannableString {
        val spannableString = SpannableString(fullText)
        val startIndexAturan = fullText.indexOf(aturanText)
        val endIndexAturan = startIndexAturan + aturanText.length
        val startIndexKebijakan = fullText.indexOf(kebijakanText)
        val endIndexKebijakan = startIndexKebijakan + kebijakanText.length

        val colorAturan = ContextCompat.getColor(requireContext(), R.color.biru_500)
        val colorKebijakan = ContextCompat.getColor(requireContext(), R.color.biru_500)

        addColorSpan(spannableString, startIndexAturan, endIndexAturan, colorAturan)
        addColorSpan(spannableString, startIndexKebijakan, endIndexKebijakan, colorKebijakan)

        return spannableString
    }

    private fun addColorSpan(
        spannableString: SpannableString,
        startIndex: Int,
        endIndex: Int,
        color: Int
    ) {
        spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, 0)
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            binding.pbRegister.visibility = View.VISIBLE
        } else {
            binding.pbRegister.visibility = View.INVISIBLE
        }
    }
}