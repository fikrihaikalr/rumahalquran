package com.fikrihaikal.qurancall.ui.teacher.detaildoateacher

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentDetailDoaTeacherBinding
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DoaItem
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class DetailDoaTeacherFragment : Fragment() {

    private var _binding : FragmentDetailDoaTeacherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailDoaTeacherViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDoaTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataDoa = arguments?.getString(Constant.KEY_DOA)!!
        viewModel.getDetailDoa(dataDoa)
        observerData()
        toDoa()
    }

    private fun observerData() {
        viewModel.detailDoaResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.pbDetail.isVisible = true
                }
                is Resource.Error ->{
                    binding.pbDetail.isVisible = false
                }
                is Resource.Success ->{
                    binding.pbDetail.isVisible = false
                    it.data?.doa?.let { data ->
                        bindView(data)
                    }
                }
            }
        }
    }

    private fun bindView(data: List<DoaItem>) {
        val doaNames = data.map { it.doaName }.toString()
        val arabDoa = data.map { it.arabDoa }.toString()
        val artiDoa = data.map { it.translateDoa }.toString()
        binding.apply {
            tvNameDoa.text = removeBrackets(doaNames)
            tvDoa.text = removeBrackets(arabDoa)
            tvArtiDoa.text = removeBrackets(artiDoa)
        }
    }

    private fun removeBrackets(input:String):String{
        return input.removePrefix("[").removeSuffix("]")
    }

    private fun toDoa() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailDoaTeacherFragment_to_doaTeacherFragment)
        }
    }
}