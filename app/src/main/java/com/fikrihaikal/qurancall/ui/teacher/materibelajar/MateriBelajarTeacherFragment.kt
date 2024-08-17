package com.fikrihaikal.qurancall.ui.teacher.materibelajar

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentMateriBelajarTeacherBinding
import com.fikrihaikal.qurancall.ui.teacher.materibelajar.adapter.MenuMateriTeacherAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class MateriBelajarTeacherFragment : Fragment() {
    private var _binding: FragmentMateriBelajarTeacherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MateriBelajarTeacherViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private lateinit var materiTeacherAdapter: MenuMateriTeacherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMateriBelajarTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.getListMenuMateri()
        setupObservers()
        toHome()

    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_materiBelajarTeacherFragment_to_homeTeacherFragment)
        }
    }

    private fun setupObservers() {
        viewModel.listMateriResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Error -> {}
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.rvMateri.isVisible = false
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    binding.rvMateri.isVisible = true
                    val menuMateriList = resources.payload?.data
                    materiTeacherAdapter.differ.submitList(menuMateriList)
                    Log.i("observeDataMenuMateri",menuMateriList.toString())
                }
            }
        }
    }

    private fun setupUI() {
        materiTeacherAdapter = MenuMateriTeacherAdapter{materiId ->
            viewModel.deleteMateriById(materiId)
        }
        binding.rvMateri.apply {
            adapter = materiTeacherAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}