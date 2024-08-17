package com.fikrihaikal.qurancall.ui.teacher.uploadmateri

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentUploadMateriBinding
import com.fikrihaikal.qurancall.domain.model.user.unggahmateri.UploadMateri
import com.fikrihaikal.qurancall.domain.usecase.unggahmateri.ValidationResultMateri
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import kotlin.properties.Delegates

class UploadMateriFragment : Fragment() {
    private var _binding : FragmentUploadMateriBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UploadMateriViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private var selectedId by Delegates.notNull<String>()
    private lateinit var materiAdapter: ArrayAdapter<String>
    private var hasFocus = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadMateriBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListMenuMateri()
        setupAutoCompleteTextView()
        setupUi()
        addMateri()
        toHome()
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_uploadMateriFragment_to_homeTeacherFragment)
        }
    }

    private fun setupUi() {
        setupTextWatchers()
        observeViewModel()
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateForm()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.edTitleMateri.addTextChangedListener(textWatcher)
        binding.edAuthorMateri.addTextChangedListener(textWatcher)
        binding.edContentMateri.addTextChangedListener(textWatcher)
    }
    private fun validateForm() {
        val uploadMateri = UploadMateri(
            title = binding.edTitleMateri.text.toString(),
            author = binding.edAuthorMateri.text.toString(),
            content = binding.edContentMateri.text.toString()
        )
        viewModel.validateUploadMateri(uploadMateri)
    }
    private fun observeViewModel() {
        viewModel.validationResult.observe(viewLifecycleOwner, Observer { result ->
            displayValidationResult(result)
        })
    }
    private fun displayValidationResult(result: ValidationResultMateri) {
        if (result.isValid) {
            clearErrors()
            binding.btnSimpan.isEnabled = true
        } else {
            binding.btnSimpan.isEnabled = false
            displayErrors(result.errors)
        }
    }
    private fun clearErrors() {
        binding.titleMateriLayout.error = null
        binding.authorMateriLayout.error = null
        binding.locationSpinnerLayout.error = null
        binding.contentMateriLayout.error = null
    }
    private fun displayErrors(errors: List<String>) {
        clearErrors()
        errors.forEach { error ->
            when {
                error.contains("Title") -> binding.titleMateriLayout.error = error
                error.contains("Author") -> binding.authorMateriLayout.error = error
                error.contains("Content") -> binding.contentMateriLayout.error = error
            }
        }
    }

//    private fun setupAutoCompleteTextView() {
//        materiAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, mutableListOf<String>())
//        binding.autoCompleteTextView.setAdapter(materiAdapter)
//        binding.autoCompleteTextView.setOnFocusChangeListener{ _, hasFocus ->
//            if (hasFocus){
//                val menuMateriList = viewModel.listMateriResponse.value?.payload?.data?.map { it.title }
//                menuMateriList?.let {
//                    materiAdapter.clear()
//                    materiAdapter.addAll(it)
//                    materiAdapter.notifyDataSetChanged()
//                }
//            }
//        }
//        binding.autoCompleteTextView.setOnItemClickListener{_, _, position, _ ->
//            val selectedTitle = materiAdapter.getItem(position)
//            selectedId = viewModel.listMateriResponse.value?.payload?.data?.get(position)?.id.orEmpty()
//            selectedTitle?.let {
//                binding.autoCompleteTextView.setText(it)
//            }
//        }
//    }
private fun setupAutoCompleteTextView() {
    materiAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf<String>())
    binding.autoCompleteTextView.setAdapter(materiAdapter)
    binding.autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            updateAutoCompleteList()
        }
    }
    binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
        val selectedTitle = materiAdapter.getItem(position)
        selectedId = viewModel.listMateriResponse.value?.payload?.data?.firstOrNull { it.title == selectedTitle }?.id
            ?: throw IllegalStateException("ID tidak ditemukan untuk judul: $selectedTitle")
        selectedTitle?.let {
            binding.autoCompleteTextView.setText(it, false)
        }
    }
}

    private fun updateAutoCompleteList() {
        val menuMateriList = viewModel.listMateriResponse.value?.payload?.data?.map { it.title }
        menuMateriList?.let {
            materiAdapter.clear()
            materiAdapter.addAll(it)
            materiAdapter.notifyDataSetChanged()
        }
    }


    private fun addMateri() {
        binding.btnSimpan.setOnClickListener {
            val title = binding.edTitleMateri.text.toString()
            val author = binding.edAuthorMateri.text.toString()
            val content = binding.edContentMateri.text.toString()
            viewModel.postMateri(AddMateriBody(selectedId.toInt(),author,title,content))
            viewModel.addMateri.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            binding.progressBar.isVisible = true
                        }
                        is Resource.Success ->{
                            binding.progressBar.isVisible = false
                            val data = it.data
                            if (data?.error == true){
                                Toast.makeText(requireContext(),"Gagal Upload Materi",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(requireContext(),"Berhasil upload materi",Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.homeTeacherFragment)
                            }
                        }
                        is Resource.Error ->{
                            binding.progressBar.isVisible = false
                            viewModel.clearState()
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}


