package com.fikrihaikal.qurancall.ui.teacher.alquranteacher.viewpager.juz

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.FragmentJuzTeacherBinding

class JuzTeacherFragment : Fragment() {
    private var _binding: FragmentJuzTeacherBinding? = null
    private val binding get() = _binding!!


    private val viewModel: JuzTeacherViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuzTeacherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.tvSurah.setOnClickListener {
            viewPager?.currentItem = 0
        }
    }
}