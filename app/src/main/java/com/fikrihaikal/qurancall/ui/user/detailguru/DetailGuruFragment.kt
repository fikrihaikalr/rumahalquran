package com.fikrihaikal.qurancall.ui.user.detailguru

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentDetailGuruBinding
import com.fikrihaikal.qurancall.network.model.response.detailguru.Data
import com.fikrihaikal.qurancall.network.model.response.detailguru.DetailGuruResponse
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import java.net.URLEncoder
import java.util.stream.Collectors.toList

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
        toList()
    }

    private fun toList(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.pilihGuruFragment)
        }
    }
    private fun toWhatsApp(phoneNumber:String, template: String) {
        binding.btnChatGuru.setOnClickListener {
            val contactNumber = phoneNumber.replace("\\s".toRegex(), "")
            val message = URLEncoder.encode(template, "UTF-8")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$contactNumber/?text=$message")
            startActivity(intent)

        }
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
                        toWhatsApp(data.numberPhone,"Assalamu'alaikum wa rahmatullahi wa barakatuh, Ustadz/Ustadzah.\n" +
                                "\n" +
                                "\n" +
                                "Dengan penuh hormat, Saya ingin menghubungi Ustadz/Ustadzah untuk melakukan setoran hafalan/konsultasi untuk mendapatkan arahan dan bimbingan dalam pemahaman Al-Quran dan agama. \n" +
                                "\n" +
                                "\n" +
                                "Apakah Ustadz/Ustadzah berkenan untuk berdiskusi lebih lanjut mengenai hal ini?\n" +
                                "\n" +
                                "\n" +
                                "Terimakasih, atas kesempatan ini. \n" +
                                "\n" +
                                "\n" +
                                "-------\n" +
                                "Regards, \n" +
                                "  Rumah Quran Online")
                    }
                }
            }
        }
    }

    private fun bindView(data: Data) {
        binding.tvNameGuru.text = data.username
        binding.tvNoTelp.text = data.numberPhone
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
