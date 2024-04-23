package com.fikrihaikal.qurancall.ui.teacher.profileteacher

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fikrihaikal.qurancall.R

class ProfileTeacherFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileTeacherFragment()
    }

    private val viewModel: ProfileTeacherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile_teacher, container, false)
    }
}