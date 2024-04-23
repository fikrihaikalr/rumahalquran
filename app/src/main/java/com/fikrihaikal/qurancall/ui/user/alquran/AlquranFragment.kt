package com.fikrihaikal.qurancall.ui.user.alquran

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentAlquranBinding
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.adapter.viewpager.ViewPagerAdapter
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.juz.JuzFragment
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.surah.SurahFragment
import com.google.android.material.textview.MaterialTextView

class AlquranFragment : Fragment() {

    private var _binding: FragmentAlquranBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AlquranFragment()
    }

    private lateinit var viewModel: AlquranViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlquranBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf<Fragment>(
            SurahFragment(),
            JuzFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        setupViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}