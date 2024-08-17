package com.fikrihaikal.qurancall.ui.user.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        toProfile()
        toHome()
        expandableAbout()
        spannableString()
        multiLineTextView()
    }

    private fun multiLineTextView() {
        binding.tvAboutRqo.text = """
            Aplikasi Rumah Quran Online adalah sebuah program dan gagasan yang dicetus oleh Yayasan Minhaj An-Nubuwwah Indonesia untuk menyediakan sebuah platform digital pembelajaran Al-Quran dan agama islam yang mudah untuk digunakan dan diakses oleh masyarakat umum. Semua sumber materi dan dokumen yang ditampilkan memiliki akses yang terbuka/open source sehingga bisa digunakan oleh siapa saja tanpa harus melakukan prosedur perizinan. Berikut Referensi yang digunakan :
            
            • Kitab Akhlaqul Lil Banat jilid 1 - 3 Karya Umar bin Ahmad Baradja
            • Kitab Mabadiul Fiqhiyah Juz 3 Karya Umar Abdul Jabbar
            • Kitab Durarul Bahiyyah Karya As-Sayyid Abu Bakr Utsman bin Muhammad Syatho Ad-Dimyathi Al-Bakri
            • Kitab Al Aqidah Al Islamiyah Basri Al Marghubi Karya Syekh Bashri bin Al-Haj Marghubi
            
            Aplikasi Rumah Quran Online dikembangkan dan dipelihara oleh:
            • Paramaresthi Windriyanj, M.Eng.
            • Yayasan Minhaj An-Nubuwwah Indonesia
            • Zain Zaidan Ahsan
            • Fathur Rizqy
            • Fikri Haikal
            • Muhammad Madani
        """.trimIndent()
    }

    private fun spannableString() {
        val fullText = getString(R.string.rqo)
        val rumahQuranText = getString(R.string.rq)
        val onlineText = getString(R.string.online)
        val spannableString = createSpannableString(fullText,rumahQuranText,onlineText)
        binding.tvRqo.text = spannableString
        
    }

    private fun createSpannableString(
        fullText: String,
        rumahQuranText: String,
        onlineText: String
    ): SpannableString {
        val spannableString = SpannableString(fullText)
        val startIndexRumahQuran = fullText.indexOf(rumahQuranText)
        val endIndexRumahQuran = startIndexRumahQuran + rumahQuranText.length
        val startIndexOnline = fullText.indexOf(onlineText)
        val endIndexOnline = startIndexOnline + onlineText.length

        val colorRumahQuran = ContextCompat.getColor(requireContext(),R.color.biru_500)
        val colorOnline = ContextCompat.getColor(requireContext(),R.color.hijau_persian_500)

        addColorSpan(spannableString, startIndexRumahQuran,endIndexRumahQuran,colorRumahQuran)
        addColorSpan(spannableString,startIndexOnline,endIndexOnline,colorOnline)

        return spannableString
    }

    private fun addColorSpan(spannableString: SpannableString, startIndex: Int, endIndex: Int, color: Int) {
        spannableString.setSpan(ForegroundColorSpan(color),startIndex,endIndex,0)
    }

    private fun expandableAbout() {
        binding.apply {
            constraintMore.setOnClickListener {
                if (expandLayoutAbout.visibility == View.GONE){
                    TransitionManager.beginDelayedTransition(constraintMore, AutoTransition())
                    expandLayoutAbout.visibility = View.VISIBLE
                    icArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                }else{
                    TransitionManager.beginDelayedTransition(constraintMore, AutoTransition())
                    expandLayoutAbout.visibility = View.GONE
                    icArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }
            }
        }
    }

    private fun toProfile() {
        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_profileFragment)
        }
    }

    private fun toHome() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_homeFragment)
        }
    }


}