package com.fikrihaikal.qurancall.ui.user.adzan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentWaktuAdzanBinding
import com.fikrihaikal.qurancall.network.model.response.adzan.KotaResponse
import com.fikrihaikal.qurancall.network.model.response.adzan.WaktuAdzanResponse
import com.fikrihaikal.qurancall.network.service.adzan.AdzanClient
import com.fikrihaikal.qurancall.ui.user.adzan.adapter.AutoCompleteAdapter
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class WaktuAdzanFragment : Fragment() {

    private var _binding: FragmentWaktuAdzanBinding? = null
    private val binding get() = _binding!!
    private lateinit var adzanViewModel: WaktuAdzanViewModel

    private val handler = Handler(Looper.getMainLooper())
    private val updateTimeTask: Runnable = object : Runnable{
        override fun run() {
            updateClock()
            handler.postDelayed(this,1000)
        }
    }

    private fun updateClock(){
        val currentTime = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime: String = simpleDateFormat.format(currentTime)
        binding.tvJamDigital.text =  formattedTime
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaktuAdzanBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.post(updateTimeTask)
        initUi()
        adzanViewModel = ViewModelProvider(this).get(com.fikrihaikal.qurancall.ui.user.adzan.WaktuAdzanViewModel::class.java)
        binding.autoCompleteTextView.threshold = 1
        val adapter = AutoCompleteAdapter(requireContext(), emptyList())
        binding.autoCompleteTextView.setAdapter(adapter)

        binding.autoCompleteTextView.setOnItemClickListener{ parent, _, position, _, ->
            val selectedCity = parent.getItemAtPosition(position) as KotaResponse
            val cityName = selectedCity.lokasi
            binding.autoCompleteTextView.setText(cityName)
            //disini bisa melakukan apa saja seperti mengambil jadwal sholat
            Toast.makeText(requireActivity(),"Anda Memilih: ${cityName}",Toast.LENGTH_SHORT).show()
            //melakukan panggilan ke API untuk mendapatkan jadwal sholat berdasarkan id kota
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            AdzanClient.api.getPrayerTimes(selectedCity.id, year, month, day)
                .enqueue(object : Callback<WaktuAdzanResponse?> {
                    override fun onResponse(
                        call: Call<WaktuAdzanResponse?>,
                        response: Response<WaktuAdzanResponse?>
                    ) {
                        if (response.isSuccessful) {
                                val jadwalAdzan = response.body()?.data?.jadwal
                                Log.e("adzan","data berhasil diterima: $jadwalAdzan")
                                binding.run {
                                    tvJadwalSubuh.text = jadwalAdzan?.subuh.toString()
                                    tvJadwalDzuhur.text = jadwalAdzan?.dzuhur.toString()
                                    tvJadwalAshar.text = jadwalAdzan?.ashar.toString()
                                    tvJadwalMaghrib.text = jadwalAdzan?.maghrib.toString()
                                    tvJadwalIsya.text = jadwalAdzan?.isya.toString()
                                }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Gagal mendapatkan jadwal sholat",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<WaktuAdzanResponse?>, t: Throwable) {
                        Toast.makeText(
                            requireContext(),
                            "Terjadi kesalahan: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        binding.autoCompleteTextView.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()){
                    adzanViewModel.searchCities(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        adzanViewModel.cities.observe(requireActivity(), Observer { cities ->
            adapter.setData(cities)
        })
    }

    private fun initUi() {
        val cal = UmmalquraCalendar()
        val formatMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val formatDay = cal.get(Calendar.DAY_OF_MONTH)
        val formatYear = cal.get(Calendar.YEAR)
        binding.tvDateMasehi.text = "$formatDay $formatMonth $formatYear"
    }

    override fun onResume() {
        super.onResume()
        binding.autoCompleteTextView.text = null
    }

}