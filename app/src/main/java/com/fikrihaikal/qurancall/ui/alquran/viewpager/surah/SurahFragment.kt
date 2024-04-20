package com.fikrihaikal.qurancall.ui.alquran.viewpager.surah

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
import androidx.viewpager2.widget.ViewPager2
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentSurahBinding
import com.fikrihaikal.qurancall.ui.alquran.viewpager.surah.adapter.SurahAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import androidx.appcompat.widget.SearchView

class SurahFragment : Fragment() {

    private var _binding : FragmentSurahBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SurahViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private lateinit var surahAdapter: SurahAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.tvJuz.setOnClickListener {
            viewPager?.currentItem = 1
        }
        setupUI()
        viewModel.getListSurah()
        setupObservers()

        // Mengatur fungsi pencarian ke SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                surahAdapter.filter.filter(newText)
                return false
            }

        })

    }



    private fun setupObservers() {
        viewModel.listSurahResponse.observe(viewLifecycleOwner){resources ->
            when(resources){
                is Resource.Success ->{
                    binding.pbHomeStory.isVisible = false
                    binding.rvSurah.isVisible = true
                    val surahList = resources.data?.data
                    surahAdapter.submitList(surahList!!)
                    Log.i("observeDataSurah", surahList.toString())
                }
                is Resource.Error ->{}
                is Resource.Loading ->{
                    binding.pbHomeStory.isVisible = true
                    binding.rvSurah.isVisible = false
                }
            }
        }
    }
    private fun setupUI() {
        surahAdapter = SurahAdapter()
        binding.rvSurah.apply {
            adapter = surahAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }
}