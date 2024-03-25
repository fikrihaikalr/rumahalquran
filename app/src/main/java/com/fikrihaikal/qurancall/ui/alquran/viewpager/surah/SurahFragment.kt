package com.fikrihaikal.qurancall.ui.alquran.viewpager.surah

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentSurahBinding

class SurahFragment : Fragment() {

    private var _binding : FragmentSurahBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SurahFragment()
    }

    private lateinit var viewModel: SurahViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.tvJuz.setOnClickListener {
            viewPager?.currentItem = 1
        }
    }

}