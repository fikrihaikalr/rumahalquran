package com.fikrihaikal.qurancall.ui.user.detailguru

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.fikrihaikal.qurancall.databinding.FragmentDetailGuruBinding
import com.fikrihaikal.qurancall.network.model.response.detailguru.Data
import com.fikrihaikal.qurancall.network.model.response.detailguru.DetailGuruResponse
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class DetailGuruFragment : Fragment() {
    private var _binding: FragmentDetailGuruBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailGuruViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailGuruBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeDetailGuru()
        val dataUsername = arguments?.getString(Constant.KEY_USERNAME)!!
        Log.d("dataDiterima", dataUsername)
        viewModel.getDetail(dataUsername)
        observeData()
    }

    private fun observeData() {
        viewModel.detailGuruResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbDetail.isVisible = true
                }

                is Resource.Error -> {
                    binding.pbDetail.isVisible = false
                }

                is Resource.Success -> {
                    binding.pbDetail.isVisible = false
                    it.data?.data?.let { data ->
                        bindView(data)
                    }
                }
            }
        }
    }

    private fun bindView(data: Data) {
        binding.tvNameGuru.text = data.username
        binding.tvNoTelp.text = data.email
    }
}

//    private fun observeDetailGuru() {
//        viewModel.getDetailGuru(navArgs.id)
//        viewModel.detailGuruResponse.observe(viewLifecycleOwner){
//            if (it != null){
//                when(it){
//                    is Resource.Loading ->{
//                        binding.pbDetail.isVisible = true
//                    }
//                    is Resource.Success ->{
//                        binding.pbDetail.isVisible = false
//                        it.data?.data.let { doa ->
//                            if (doa != null){
//                                bindToViewBook(doa)
//                                Log.d("detailDiterima", doa.toString())
//                                Log.d("detailId",doa.id)
//                            }
//                        }
//                    }
//                    is Resource.Error ->{
//                        binding.pbDetail.isVisible = false
//                        Log.d("detailGagal","GAGAL")
//                    }
//                }
//            }
//        }
//    }

//    private fun bindToViewBook(data: Data) =
//        with(binding){
//            tvNameGuru.text =data.username
//            tvNoTelp.text=data.email
//            Log.d("bindView", data.username)
//        }
