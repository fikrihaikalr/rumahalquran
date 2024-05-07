package com.fikrihaikal.qurancall.ui.teacher.hometeacher

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentHomeTeacherBinding
import com.fikrihaikal.qurancall.network.model.data.home.getItemMenuList
import com.fikrihaikal.qurancall.network.model.data.home.getItemMenuTeacherList
//import com.fikrihaikal.qurancall.network.model.data.home.getItemMenuTeacherList
import com.fikrihaikal.qurancall.ui.teacher.hometeacher.adapter.HomeMenuTeacherAdapter
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeTeacherFragment : Fragment() {
    private var _binding: FragmentHomeTeacherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeTeacherViewModel by viewModels(){
        ViewModelFactory(requireContext())
    }
    private val menuTeacherAdapter: HomeMenuTeacherAdapter by lazy {
        HomeMenuTeacherAdapter {
            findNavController().navigate(it.navigation)
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    private val updateTimeTask: Runnable = object : Runnable {
        override fun run() {
            updateClock()
            handler.postDelayed(this, 1000)
        }
    }

    private fun updateClock() {
        val currentTime = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime: String = simpleDateFormat.format(currentTime)
        binding.digitalClock.text = formattedTime
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.post(updateTimeTask)
        initUI()
        setRvMenuTeacher()
        viewModel.getUser()
        observeUser()
        toSetting()
    }

    private fun toSetting() {
        binding.icSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeTeacherFragment_to_settingTeacherFragment)
        }
    }


    private fun observeUser() = with(binding) {
        viewModel.userResponse.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val username = resource.data?.username
                    Log.i("observeUser resource", "$username")
                    binding.tvName.text = username
                }

                is Resource.Error -> {
                    // Tanggapan error: tangani kesalahan jika terjadi
                    Toast.makeText(
                        requireContext(),
                        "Error: ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    // Tanggapan sedang dimuat: tampilkan indikator loading atau lakukan tindakan yang sesuai
                }
            }
        }

    }


    private fun initUI() {
        val cal = UmmalquraCalendar()
        val formatMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val formatDay = cal.get(Calendar.DAY_OF_MONTH)
        val formatYear = cal.get(Calendar.YEAR)
        binding.dateMasehi.text = "$formatDay $formatMonth $formatYear"
    }

    private fun setRvMenuTeacher() = with(binding) {
        rvMenu.adapter = menuTeacherAdapter
        rvMenu.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvMenu.setHasFixedSize(true)
        menuTeacherAdapter.setItems(getItemMenuTeacherList(requireContext()))
        rvMenu.setOnTouchListener { _, event ->
            return@setOnTouchListener event.actionMasked == MotionEvent.ACTION_MOVE
        }
    }

}