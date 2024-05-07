package com.fikrihaikal.qurancall.ui.teacher.uploadmateri

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentUploadMateriBinding
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriBody
import com.fikrihaikal.qurancall.network.model.response.menumateri.DataItem
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriBody
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadMateriBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.getListMenuMateri()
//        // Buat adapter untuk AutoCompleteTextView
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf<DataItem>())
//        binding.autoCompleteTextView.setAdapter(adapter)
//
//        binding.autoCompleteTextView.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                // Mengambil daftar materi saat AutoCompleteTextView mendapatkan fokus
//                val menuMateriList =
//                    viewModel.listMateriResponse.value?.payload?.data?.map { it }
//                menuMateriList?.let {
//                    adapter.clear()
//                    adapter.addAll(it)
//                    adapter.notifyDataSetChanged()
//                }
//            }
//        }
//        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
//            val selectedTitle = adapter.getItem(position)
//            selectedId = adapter.getItem(position)?.id.orEmpty()
//            selectedTitle?.let {
//                binding.autoCompleteTextView.setText(it.title)
//            }
//        }
        viewModel.getListMenuMateri()
        setupAutoCompleteTextView()

        addMateri()
    }

    private fun setupAutoCompleteTextView() {
        materiAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, mutableListOf<String>())
        binding.autoCompleteTextView.setAdapter(materiAdapter)
        binding.autoCompleteTextView.setOnFocusChangeListener{ _, hasFocus ->
            if (hasFocus){
                val menuMateriList = viewModel.listMateriResponse.value?.payload?.data?.map { it.title }
                menuMateriList?.let {
                    materiAdapter.clear()
                    materiAdapter.addAll(it)
                    materiAdapter.notifyDataSetChanged()
                }
            }
        }
        binding.autoCompleteTextView.setOnItemClickListener{_, _, position, _ ->
            val selectedTitle = materiAdapter.getItem(position)
            selectedId = viewModel.listMateriResponse.value?.payload?.data?.get(position)?.id.orEmpty()
            selectedTitle?.let {
                binding.autoCompleteTextView.setText(it)
            }
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
                        is Resource.Error ->{}
                    }
                }
            }
        }
    }
}