package com.fikrihaikal.qurancall.ui.pilihguru

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.databinding.FragmentPilihGuruBinding
import com.fikrihaikal.qurancall.ui.pilihguru.adapter.GuruAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class PilihGuruFragment : Fragment() {
    private var _binding: FragmentPilihGuruBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihGuruViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private lateinit var guruAdapter: GuruAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihGuruBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        viewModel.getListGuru()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.listGuruResponse.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.pbHomeStory.isVisible = false
                    binding.rvGuru.isVisible = true
                    val doaList = resource.data?.data ?: emptyList()
                    doaList.filterNotNull()
                    guruAdapter.differ.submitList(doaList)
                    Log.i("observe data ", "$doaList")
                }

                is Resource.Error -> {}
                is Resource.Loading -> {
                    binding.pbHomeStory.isVisible = true
                    binding.rvGuru.isVisible = false
                }
            }
        }
    }
    private fun setupUI() {
        guruAdapter = GuruAdapter()
        binding.rvGuru.apply {
            adapter = guruAdapter
            layoutManager =
                LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}


//    private fun navigateToDetailGuru(id:String){
//        findNavController().navigate(PilihGuruFragmentDirections.actionPilihGuruFragmentToDetailGuruFragment(id))
//        Log.d("kirimDetail","berhasil")
//    }