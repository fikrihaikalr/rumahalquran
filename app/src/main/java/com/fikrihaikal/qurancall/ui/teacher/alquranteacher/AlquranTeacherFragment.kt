package com.fikrihaikal.qurancall.ui.teacher.alquranteacher

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentAlquranBinding
import com.fikrihaikal.qurancall.databinding.FragmentAlquranTeacherBinding
import com.fikrihaikal.qurancall.ui.teacher.alquranteacher.viewpager.juz.JuzTeacherFragment
import com.fikrihaikal.qurancall.ui.teacher.alquranteacher.viewpager.surah.SurahTeacherFragment
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.adapter.viewpager.ViewPagerAdapter
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.juz.JuzFragment
import com.fikrihaikal.qurancall.ui.user.alquran.viewpager.surah.SurahFragment

class AlquranTeacherFragment : Fragment() {
    private var _binding: FragmentAlquranTeacherBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AlquranTeacherFragment()
    }

    private val viewModel: AlquranTeacherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlquranTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragmentList = arrayListOf<Fragment>(
            SurahTeacherFragment(),
            JuzTeacherFragment()
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