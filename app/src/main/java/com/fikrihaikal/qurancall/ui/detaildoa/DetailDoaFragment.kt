package com.fikrihaikal.qurancall.ui.detaildoa


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fikrihaikal.qurancall.databinding.FragmentDetailDoaBinding
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.fikrihaikal.qurancall.utils.dataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DoaItem
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource

class DetailDoaFragment : Fragment() {

    private var _binding: FragmentDetailDoaBinding? = null
    private val binding get() = _binding!!
    private val detailDoaViewModel: DetailDoaViewModel by viewModels {
        ViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDoaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        observeDetailDoa()
        val dataUsername = arguments?.getString(Constant.KEY_DOA)!!
        detailDoaViewModel.getDetailDoa(dataUsername)
        observerData()
        toDoa()
    }

    private fun observerData() {
        detailDoaViewModel.detailDoaResponse.observe(viewLifecycleOwner){
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

    private fun toDoa() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailDoaFragment_to_doaFragment)
        }
    }

//    private fun observeDetailDoa() {
//        val dataStore: DataStore<Preferences> = requireContext().dataStore
//        tokenPreferences = TokenPreferences.getInstance(dataStore)
//        val token = TokenPreferences.getTokenSynchronously(dataStore).toString()
//        detailDoaViewModel.getDetailDoa(token,navArgs.id.toString())
//        detailDoaViewModel.detailDoa.observe(viewLifecycleOwner){
//            if (it != null){
//                when(it){
//                    is Resource.Loading ->{
//                        binding.pbDetail.isVisible = true
//                    }
//                    is Resource.Success ->{
//                        binding.pbDetail.isVisible = false
//                        it.data?.doa.let { doa ->
//                            if (doa != null){
//                                setDetailView(doa)
//                            }
//                        }
//                    }
//                    is Resource.Error ->{
//                        binding.pbDetail.isVisible = false
//                    }
//                }
//            }
//        }
//    }

//    private fun setDetailView(doa: List<DoaItem>?) {
//        binding.apply {
//            if (!doa.isNullOrEmpty()){
//                val doaNames = doa.map { it.doaName }.toString()
//                val arabDoa = doa.map { it.arabDoa }.toString()
//                val artiDoa = doa.map { it.translateDoa }.toString()
//                tvNameDoa.text = removeBrackets(doaNames)
//                tvDoa.text = removeBrackets(arabDoa)
//                tvArtiDoa.text = removeBrackets(artiDoa)
//
//            }
//        }
//    }

    private fun removeBrackets(input:String):String{
        return input.removePrefix("[").removeSuffix("]")
    }

}