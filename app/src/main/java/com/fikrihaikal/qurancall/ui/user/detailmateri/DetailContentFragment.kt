package com.fikrihaikal.qurancall.ui.user.detailmateri

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
import com.fikrihaikal.qurancall.databinding.FragmentDetailContentBinding
import com.fikrihaikal.qurancall.network.model.response.detailcontentmateri.Data
import com.fikrihaikal.qurancall.ui.user.detailmateri.adapter.DetailContentMateriAdapter
import com.fikrihaikal.qurancall.ui.user.detailsurah.adapter.DetailSurahAdapter
import com.fikrihaikal.qurancall.ui.user.listsubmateri.adapter.SubMenuMateriAdapter
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class DetailContentFragment : Fragment() {
    private var _binding: FragmentDetailContentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailContentViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    private val idMateri: String by lazy {
        arguments?.getString(Constant.KEY_SUB_MENU_ID).orEmpty() // ""
    }

    private val idSubMateri: String by lazy {
        arguments?.getString(Constant.KEY_MENU_MATERI).orEmpty() // ""
    }

    private val detailContentMateriAdapter: DetailContentMateriAdapter by lazy {
        DetailContentMateriAdapter {
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailContentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailContentMateri(idMateri)
//        setupUI()
        observeDetailContentMateri()
        toSubMateri()
        Log.d("ID SUB MATERI DETAIL", idSubMateri)
    }

    private fun toSubMateri() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.subMenuMateriFragment, Bundle().apply {
                putString(Constant.KEY_MENU_MATERI, idSubMateri)
            })

        }
    }

    private fun observeDetailContentMateri() {
        viewModel.detailContentResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Log.d("error", it.message!!)
                }
                is Resource.Success ->{
                    binding.progressBar.isVisible = false
                    it.data?.data?.let { data ->
                        bindView(data)
                        Log.d("OBS DATA DETAIL CONTENT",data.toString())
//                        detailContentMateriAdapter.setItems(it.data.data)
                    }
                }
            }
        }
    }

    private fun bindView(data: Data) {
        val nameTitle = data.title
        val nameAuthor = data.author
        val contentMateri = data.content
        binding.apply {
            tvTitle.text = nameTitle
            tvAuthor.text = nameAuthor
            tvContent.text = contentMateri
        }
    }

//    private fun setupUI() {
//        binding.rvDetailContent.apply {
//            adapter = detailContentMateriAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            setHasFixedSize(true)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}