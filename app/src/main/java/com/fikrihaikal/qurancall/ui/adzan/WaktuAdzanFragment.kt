package com.fikrihaikal.qurancall.ui.adzan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.fikrihaikal.qurancall.ui.adzan.adapter.AutoCompleteAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class WaktuAdzanFragment : Fragment() {

    private var _binding: FragmentWaktuAdzanBinding? = null
    private val binding get() = _binding!!
    private lateinit var adzanViewModel: WaktuAdzanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaktuAdzanBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adzanViewModel = ViewModelProvider(this).get(WaktuAdzanViewModel::class.java)
        binding.autoCompleteTextView.threshold = 1 //Setelah mengetik satu karakter, AutoCompleteTextView akan mulai menampilkan saran.
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
//                            val waktuAdzanResponse = response.body()
//
////                            val jadwalAdzan = response.body()?.data?.jadwal
//                            if (waktuAdzanResponse != null && waktuAdzanResponse.data != null){
                                val jadwalAdzan = response.body()?.data?.jadwal
                                Log.e("adzan","data berhasil diterima: $jadwalAdzan")
                                // Set nilai jadwal sholat ke TextView sesuai dengan id-nya
                                binding.run {
                                    tvJadwalSubuh.text = jadwalAdzan?.subuh.toString()
                                    tvJadwalDzuhur.text = jadwalAdzan?.dzuhur.toString()
                                    tvJadwalAshar.text = jadwalAdzan?.ashar.toString()
                                    tvJadwalMaghrib.text = jadwalAdzan?.maghrib.toString()
                                    tvJadwalIsya.text = jadwalAdzan?.isya.toString()
                                }
//                            }
                            // Lanjutkan untuk semua jadwal sholat yang Anda inginkan
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

    override fun onResume() {
        super.onResume()
        binding.autoCompleteTextView.text = null
    }

}