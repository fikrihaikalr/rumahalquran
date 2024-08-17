package com.fikrihaikal.qurancall.ui.teacher.tambahkategory

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentTambahKategoriBinding
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriBody
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class TambahKategoriFragment : Fragment() {
    private var _binding: FragmentTambahKategoriBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TambahKategoriViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTambahKategoriBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCategory()
        toHome()
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_tambahKategoriFragment_to_homeTeacherFragment)
        }
    }

    private fun addCategory() {
        binding.btnSimpan.setOnClickListener {
            val title = binding.edAddCategory.text.toString()
            viewModel.category(TambahKategoriBody(title))
            viewModel.addCategory.observe(viewLifecycleOwner){
                if (it != null){
                    when(it){
                        is Resource.Loading ->{
                            binding.progressBar.isVisible = true
                        }
                        is Resource.Success ->{
                            binding.progressBar.isVisible = false
                            viewModel.clearUploadState()
                            val data = it.data
                            if (data?.error == true){
                                Toast.makeText(requireContext(),"Gagal Tambah Category",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(requireContext(),"Berhasil Tambah Category",Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_tambahKategoriFragment_to_homeTeacherFragment)
                            }
                        }
                        is Resource.Error ->{
                            binding.progressBar.isVisible = false
                            viewModel.clearUploadState()
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}