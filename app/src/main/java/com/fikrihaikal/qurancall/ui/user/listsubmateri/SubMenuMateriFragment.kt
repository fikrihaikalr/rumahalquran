package com.fikrihaikal.qurancall.ui.user.listsubmateri

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
import com.fikrihaikal.qurancall.databinding.FragmentSubMenuMateriBinding
import com.fikrihaikal.qurancall.network.model.response.submenumateri.DataItem
import com.fikrihaikal.qurancall.ui.user.listsubmateri.adapter.SubMenuMateriAdapter
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory

class SubMenuMateriFragment : Fragment() {

    private var _binding: FragmentSubMenuMateriBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubMenuMateriViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }

    private val idMateri: String by lazy {
        arguments?.getString(Constant.KEY_MENU_MATERI).orEmpty() // ""
    }


    private val subMenuMateriAdapter: SubMenuMateriAdapter by lazy {
        SubMenuMateriAdapter { data ->
            findNavController().navigate(
                R.id.action_subMenuMateriFragment_to_detailContentFragment,
                Bundle().apply {
                    putString(Constant.KEY_SUB_MENU_ID, data.id)
                    putString(Constant.KEY_MENU_MATERI, idMateri)
                })
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubMenuMateriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSubMenuMateri(idMateri)
        setupUI()
        observeSubMenuMateri()
        toListMenuMateri()
        Log.d("ID SUB MATERI", idMateri.toString())

    }

    private fun observeSubMenuMateri() {
        viewModel.subMenuMateriResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Log.d("error", it.message!!)
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    it.data?.data?.let { data ->
                        Log.d("OBS DATA SUB MATERI", data.toString())
                        subMenuMateriAdapter.setItems(it.data.data)
                    }
                }
            }
        }
    }


    private fun setupUI() {
        binding.rvSubMenuMateri.apply {
            adapter = subMenuMateriAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun toListMenuMateri() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_subMenuMateriFragment_to_materiBelajarFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

