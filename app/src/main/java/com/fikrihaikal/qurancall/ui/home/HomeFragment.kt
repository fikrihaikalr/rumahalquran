package com.fikrihaikal.qurancall.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.ui.home.adapters.home.HomeMenuAdapter
import com.fikrihaikal.qurancall.databinding.FragmentHomeBinding
import com.fikrihaikal.qurancall.network.model.data.home.getItemMenuList
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import com.fikrihaikal.qurancall.utils.dataStore
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val menuAdapter: HomeMenuAdapter by lazy {
        HomeMenuAdapter {
            findNavController().navigate(it.navigation)
        }
    }
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireContext())
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
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.post(updateTimeTask)
        initUI()
        setRvMenu()
        homeViewModel.getUser()
        observeUser()
        toSetting()

    }

    private fun toSetting() {
        binding.icSetting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }
    }

    private fun initUI() {
        val cal = UmmalquraCalendar()
        val formatMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val formatDay = cal.get(Calendar.DAY_OF_MONTH)
        val formatYear = cal.get(Calendar.YEAR)
        binding.dateMasehi.text = "$formatDay $formatMonth $formatYear"
    }

    private fun setRvMenu() = with(binding) {
        rvMenu.adapter = menuAdapter
        rvMenu.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvMenu.setHasFixedSize(true)
        menuAdapter.setItems(getItemMenuList(requireContext()))
        rvMenu.setOnTouchListener { _, event ->
            return@setOnTouchListener event.actionMasked == MotionEvent.ACTION_MOVE
        }
    }


    private fun observeUser() = with(binding) {
        homeViewModel.userResponse.observe(viewLifecycleOwner) { resource ->
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
}


