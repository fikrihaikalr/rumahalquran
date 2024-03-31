package com.fikrihaikal.qurancall.ui.doa


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentDoaBinding
import com.fikrihaikal.qurancall.ui.doa.adapter.DoaAdapter
import com.fikrihaikal.qurancall.ui.doa.adapter.LoadingStateAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DoaFragment : Fragment() {

    private var _binding: FragmentDoaBinding? = null
    private val binding get() = _binding!!
    private val doaViewModel: DoaViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private lateinit var doaAdapter: DoaAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHome()
        setAdapter()
        doaViewModel.getAllDoa()
        observerDoa()
    }
    private fun observerDoa() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            doaViewModel.listDoa.collectLatest { pagingData ->
//                doaAdapter.submitData(pagingData)
//            }
//        }
        doaViewModel.listDoa.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(doaAdapter::submitData).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setAdapter() {
        doaAdapter = DoaAdapter()
        binding.rvDoa.adapter = doaAdapter.withLoadStateFooter(footer = LoadingStateAdapter{doaAdapter.retry()})
        binding.rvDoa.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        doaAdapter.addLoadStateListener {
            when(it.source.refresh){
                is LoadState.NotLoading ->{
                    binding.pbHomeStory.visibility = View.GONE
                }
                is LoadState.Loading ->{
                    binding.pbHomeStory.visibility = View.VISIBLE
                }
                is LoadState.Error ->{
                    binding.pbHomeStory.visibility = View.GONE
                }
            }
        }
    }



    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_doaFragment_to_homeFragment)
        }
    }
}
//    private val doaAdapter:DoaAdapter by lazy {
//        DoaAdapter{
//
//        }
//    }
//        setupUI()
//        doaViewModel.getListDoa()
//        setupObservers()
//    private fun setupObservers() {
//            doaViewModel.listDoaResponse.observe(viewLifecycleOwner){ resource ->
//                when(resource){
//                    is Resource.Success ->{
//                        binding.pbHomeStory.isVisible = false
//                        binding.rvDoa.isVisible = true
//                        val doaList = resource.data?.data ?: emptyList()
//                        doaList.filterNotNull()
//                        doaAdapter.setItems(doaList)
//                        Log.i("observe data ", "$doaList")
//                    }
//                    is Resource.Error ->{}
//                    is Resource.Loading ->{
//                        binding.pbHomeStory.isVisible = true
//                        binding.rvDoa.isVisible = false
//                    }
//                }
//            }
//    }

//    private fun setupUI() {
//        binding.rvDoa.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = doaAdapter
//        }
//    }
//    private fun setupRecyclerView() {
//        doaAdapter = DoaAdapter(emptyList())
//        binding.rvDoa.apply {
//            adapter = doaAdapter
//            layoutManager = LinearLayoutManager(context)
//
//        }
//    }
//
//    private fun observeViewModel() {
//        doaViewModel.doaListResponse.observe(viewLifecycleOwner, Observer { resource ->
//            when (resource) {
//                is Resource.Success ->{
//                    resource.data?.let { doaResponse ->
//                        Log.i("observeUser resource", "data berhasil di get")
//                        val doaList = doaResponse.doa ?: emptyList()
//                        if (doaList.isNotEmpty()){
//                            updateDoaList(doaList.filterNotNull())
//                        }else{
//                            Log.i("adapter ","data gagal di get")
//                        }
//                    }
//                }
//                is Resource.Error -> {
//                    // Handle error
//
//                }
//                is Resource.Loading -> {
//                    // Handle loading
//                }
//            }
//        })
//    }
//
//    private fun fetchData() {
//        doaViewModel.getListDoa()
//    }
//
//    private fun updateDoaList(newDoaList: List<DoaItem>) {
//        doaAdapter.updateData(newDoaList)
//    }



//    private fun observeDoa() {
//        doaViewModel.getAllDoa()
//        doaViewModel.listDoa.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
//            .onEach(doaAdapter::submitData).launchIn(viewLifecycleOwner.lifecycleScope)
//    }
//    private fun setAdapter() {
//        doaAdapter = DoaAdapter()
//        binding.rvDoa.adapter = doaAdapter.withLoadStateFooter(footer = LoadingStateAdapter{doaAdapter.retry()})
//        binding.rvDoa.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//
//        doaAdapter.addLoadStateListener {
//            when(it.source.refresh){
//                is LoadState.NotLoading ->{
//                    binding.pbHomeStory.visibility = View.GONE
//                }
//                is LoadState.Loading ->{
//                    binding.pbHomeStory.visibility = View.VISIBLE
//                }
//                is LoadState.Error ->{
//                    binding.pbHomeStory.visibility = View.GONE
//                }
//            }
//        }
//    }