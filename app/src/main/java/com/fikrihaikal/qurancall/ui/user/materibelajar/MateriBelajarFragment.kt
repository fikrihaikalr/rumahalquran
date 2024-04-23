package com.fikrihaikal.qurancall.ui.user.materibelajar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentMateriBelajarBinding
import com.fikrihaikal.qurancall.ui.user.materibelajar.adapter.MenuMateriAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class MateriBelajarFragment : Fragment() {
    private var _binding: FragmentMateriBelajarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MateriBelajarViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private lateinit var materiAdapter: MenuMateriAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMateriBelajarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.getListMenuMateri()
        setupObservers()
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
                    materiAdapter.differ.submitList(menuMateriList)
                    Log.i("observeDataMenuMateri",menuMateriList.toString())
                }
            }
        }
    }

    private fun setupUI() {
        materiAdapter = MenuMateriAdapter()
        binding.rvMateri.apply {
            adapter = materiAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}