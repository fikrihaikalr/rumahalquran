package com.fikrihaikal.qurancall.ui.user.detailsurah

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentDetailSurahBinding
import com.fikrihaikal.qurancall.network.model.response.detailsurah.Data
import com.fikrihaikal.qurancall.ui.user.detailsurah.adapter.DetailSurahAdapter
import com.fikrihaikal.qurancall.utils.Constant
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import kotlinx.coroutines.launch
import java.io.IOException

class DetailSurahFragment : Fragment() {
    private var _binding: FragmentDetailSurahBinding? =null
    private val binding get() = _binding!!
    private val viewModel: DetailSurahViewModel by viewModels() {
        ViewModelFactory(requireContext())
    }
    private lateinit var detailSurahAdapter: DetailSurahAdapter
    private var mediaPlayer:MediaPlayer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailSurahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idSurah = arguments?.getString(Constant.KEY_SURAH)!!
        Log.d("dataDiterima",idSurah)
        viewModel.getDetail(idSurah)
        observeDetail()
        setupUI()
        toListSurah()

    }

    private fun toListSurah() {
        binding.btnBack.setOnClickListener {
            mediaPlayer?.stop()
            findNavController().navigate(R.id.action_detailSurahFragment_to_surahFragment)
        }
    }

    private fun observeDetail() {
        viewModel.detailsurahResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.pbDetail.isVisible = true
                }
                is Resource.Error ->{
                    binding.pbDetail.isVisible = false
                    Log.e("error", it.message!!)
                }
                is Resource.Success ->{
                    binding.pbDetail.isVisible = false
                    it.data?.data?.let { data ->
                        bindView(data)
                        Log.d("OBS DATA", data.toString())
                        detailSurahAdapter.differ.submitList(data.ayahData)
                        playAudio(data.audioUrl)
                    }
                }
            }
        }
    }
    private fun playAudio(path:String){
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
            }
        }
        binding.fabPlay.setOnClickListener {
            try {

                mediaPlayer?.apply {
                    reset()
                    setDataSource(path)
                    prepare()
                    start()
                }
            }catch (e:IOException){
                e.printStackTrace()
            }
            binding.fabPlay.visibility = View.GONE
            binding.fabStop.visibility = View.VISIBLE
        }

        binding.fabStop.setOnClickListener {
            mediaPlayer?.stop()
            binding.fabPlay.visibility = View.VISIBLE
            binding.fabStop.visibility = View.GONE
        }
    }
//    private fun playAudio(path: String) {
//        val mediaPlayer = MediaPlayer()
//        binding.fabPlay.setOnClickListener {
//            try {
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//                mediaPlayer.setDataSource(path)
//                mediaPlayer.prepare()
//                mediaPlayer.start()
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
//            binding.fabPlay.visibility = View.GONE
//            binding.fabStop.visibility = View.VISIBLE
//        }
//
//        binding.fabStop.setOnClickListener {
//            mediaPlayer.stop()
//            mediaPlayer.reset()
//            binding.fabPlay.visibility = View.VISIBLE
//            binding.fabStop.visibility = View.GONE
//        }
//    }

    private fun bindView(data: Data) {
        val nameSurah = data.surahName
        val artiSurah = data.translateId
        binding.apply {
            tvNameSurah.text = nameSurah
            tvTranslateSurah.text = artiSurah
        }
    }

    private fun setupUI() {
        detailSurahAdapter = DetailSurahAdapter()
        binding.rvDetailSurah.apply {
            adapter = detailSurahAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }


}