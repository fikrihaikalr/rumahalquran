package com.fikrihaikal.qurancall.ui.detailsurah

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fikrihaikal.qurancall.R

class DetailSurahFragment : Fragment() {

    companion object {
        fun newInstance() = DetailSurahFragment()
    }

    private val viewModel: DetailSurahViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_surah, container, false)
    }
}