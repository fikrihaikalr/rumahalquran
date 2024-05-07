package com.fikrihaikal.qurancall.ui.teacher.doateacher

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentDoaTeacherBinding
import com.fikrihaikal.qurancall.ui.teacher.doateacher.adapter.DoaTeacherAdapter
import com.fikrihaikal.qurancall.ui.user.doa.adapter.DoaAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class DoaTeacherFragment : Fragment() {
    private var _binding: FragmentDoaTeacherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DoaTeacherViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private lateinit var doaTeacherAdapter: DoaTeacherAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoaTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.getListDoa()
        setupObservers()
        backToHome()
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_doaTeacherFragment_to_homeTeacherFragment)
        }
    }

    private fun setupObservers() {
        viewModel.listDoaResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Success ->{
                    binding.pbHomeStory.isVisible = false
                    binding.rvDoa.isVisible = true
                    val doaList = resources.data?.data ?: emptyList()
                    doaList.filterNotNull()
                    doaTeacherAdapter.differ.submitList(doaList)
                }
                is Resource.Error ->{}
                is Resource.Loading ->{
                    binding.pbHomeStory.isVisible = true
                    binding.rvDoa.isVisible = false
                }
            }
        }
    }

    private fun setupUI() {
        doaTeacherAdapter = DoaTeacherAdapter()
        binding.rvDoa.apply {
            adapter = doaTeacherAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}