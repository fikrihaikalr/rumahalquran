package com.fikrihaikal.qurancall.ui.teacher.submateriteacher

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
import com.fikrihaikal.qurancall.databinding.FragmentSubMenuMateriTeacherBinding
import com.fikrihaikal.qurancall.ui.teacher.submateriteacher.adapter.SubMenuMateriTeacherAdapter
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class SubMenuMateriTeacherFragment : Fragment() {
    private var _binding: FragmentSubMenuMateriTeacherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubMenuMateriTeacherViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }

    private val idMateri:String by lazy {
        arguments?.getString(Constant.KEY_MENU_MATERI).orEmpty()
    }
//    private val subMenuMateriTeacherAdapter: SubMenuMateriTeacherAdapter by lazy {
//        SubMenuMateriTeacherAdapter{ data ->
//            findNavController().navigate(
//                R.id.action_subMenuMateriTeacherFragment_to_detailContentTeacherFragment,
//                Bundle().apply {
//                    putString(Constant.KEY_SUB_MENU_ID,data.id)
//                    putString(Constant.KEY_MENU_MATERI, idMateri)
//                }
//            )
//        }
//    }
    private val subMenuMateriTeacherAdapter: SubMenuMateriTeacherAdapter by lazy {
        SubMenuMateriTeacherAdapter(
            itemClick = {data ->
                findNavController().navigate(
                    R.id.action_subMenuMateriTeacherFragment_to_detailContentTeacherFragment,
                    Bundle().apply {
                        putString(Constant.KEY_SUB_MENU_ID,data.id)
                        putString(Constant.KEY_MENU_MATERI,idMateri)
                    }
                )
            },
            deleteClick = {data ->
                viewModel.deleteSubMateriById(data.id)
            }
        )
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubMenuMateriTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSubMenuMateriTeacher(idMateri)
        setupUI()
        observeSubMenuMateriTeacher()
        observeDeleteSubMateri()
        toListMenuMateri()
    }

    private fun observeDeleteSubMateri() {
        viewModel.deleteSubMateriResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{}
                is Resource.Success ->{
                    viewModel.getSubMenuMateriTeacher(idMateri)
                }
                is Resource.Error ->{}
            }
        }
    }

    private fun observeSubMenuMateriTeacher() {
        viewModel.subMenuMateriTeacherResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.progressBar.isVisible = true
                }
                is Resource.Error ->{
                    binding.progressBar.isVisible = false
                    Log.d("Error", it.message!!)
                }
                is Resource.Success ->{
                    binding.progressBar.isVisible = false
                    it.data?.data?.let { data ->
                        Log.d("OBS DATA SUB MATERI", data.toString())
                        subMenuMateriTeacherAdapter.setItems(it.data.data)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.rvSubMenuMateri.apply {
            adapter = subMenuMateriTeacherAdapter
            layoutManager =
                LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

    private fun toListMenuMateri(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_subMenuMateriTeacherFragment_to_materiBelajarTeacherFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}