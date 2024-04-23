package com.fikrihaikal.qurancall.ui.user.sambungayat.detailsambungayat


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentDetailSambungAyatBinding
import com.fikrihaikal.qurancall.ui.user.sambungayat.pilihsurah.PilihSurahFragment

class DetailSambungAyatFragment : Fragment(){
    private var _binding: FragmentDetailSambungAyatBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailSambungAyatViewModel
    private var nomorSuratTerpilih: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailSambungAyatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSambungAyat()
        toHome()
        gantiSurah()
    }

    private fun observeSambungAyat() {
        viewModel = ViewModelProvider(this).get(DetailSambungAyatViewModel::class.java)
        if (nomorSuratTerpilih == null) {
            showPilihSuratDialog()
        } else {
            loadNewQuestion(nomorSuratTerpilih!!)
        }

        viewModel.currentAyat.observe(viewLifecycleOwner) { ayat ->
            binding.tvAyatNumber.text = ayat.nomorAyat.toString()
            binding.tvAyatText.text = ayat.teksArab
            binding.tvAyatLatin.text = ayat.teksLatin
        }
        val radioGroup = view?.findViewById<RadioGroup>(R.id.radioJawaban)
        viewModel.answerOptions.observe(viewLifecycleOwner) { options ->
            radioGroup?.removeAllViews() // Clear previous options
            options.forEachIndexed { index, option ->
                val radioButton = RadioButton(context).apply {
                    text = option
                    id = View.generateViewId()
                    setPadding(16, 16, 16, 16)
                    layoutParams = RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT).apply {
                        bottomMargin = 10
                    }
                    background = context?.getDrawable(R.drawable.search_view_bg)
                    setTextColor(resources.getColor(R.color.black))
                    typeface = ResourcesCompat.getFont(context, R.font.inter_medium)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f) // 18sp
                }
                radioGroup?.addView(radioButton)
                if (index == 0) radioGroup?.check(radioButton.id)
                binding.btnJawab.isEnabled = true
            }
        }

        binding.btnJawab.isEnabled = false // Disable the button initially
        binding.btnJawab.setOnClickListener {
            val selectedRadioButtonId = radioGroup?.checkedRadioButtonId
            val selectedAnswer = selectedRadioButtonId?.let { it1 ->
                view?.findViewById<RadioButton>(
                    it1
                )?.text.toString()
            }
            // Log untuk debugging
            Log.d("AyatFragment", "Selected Answer: $selectedAnswer")
            Log.d("AyatFragment", "Correct Answer: ${viewModel.correctAnswer}")
            val isCorrect = selectedAnswer?.let { it1 -> viewModel.checkAnswer(it1) }
            if (isCorrect == true) {
                nomorSuratTerpilih?.let { loadNewQuestion(it) }
                Toast.makeText(context, "Jawaban Anda benar!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Jawaban Anda salah, coba lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gantiSurah() {
        binding.tvGantiSurah.setOnClickListener {
            showPilihSuratDialog()
        }
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun loadNewQuestion(suratNumber: Int) {
        viewModel.loadAyat(suratNumber)
        binding.btnJawab.isEnabled = false // Nonaktifkan tombol sampai jawaban dimuat
    }

    private fun showPilihSuratDialog() {
        val pilihSuratFragment = PilihSurahFragment().apply {
            setTargetFragment(this@DetailSambungAyatFragment, 1)
        }
        pilihSuratFragment.show(parentFragmentManager, "PilihSuratFragment")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            nomorSuratTerpilih = data?.getIntExtra("nomorSurat", -1) ?: -1
            if (nomorSuratTerpilih != -1) {
                loadNewQuestion(nomorSuratTerpilih!!)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Reset nomorSuratTerpilih ketika fragment tidak lagi terlihat
        nomorSuratTerpilih = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}