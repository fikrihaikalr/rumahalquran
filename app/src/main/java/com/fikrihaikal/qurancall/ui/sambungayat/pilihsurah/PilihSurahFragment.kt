package com.fikrihaikal.qurancall.ui.sambungayat.pilihsurah

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.qurancall.databinding.FragmentPilihSurahBinding
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.ViewModelFactory
import android.app.Activity.RESULT_OK
import androidx.navigation.fragment.findNavController
import com.fikrihaikal.qurancall.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PilihSurahFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPilihSurahBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PilihSurahViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilihSurahBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        val listView: ListView = binding.listSurat
        viewModel.loadDaftarSurat()
        viewModel.daftarSurat.observe(viewLifecycleOwner) { listSurat ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listSurat.map { it.namaLatin ?: "" })
            listView.adapter = adapter
            binding.progressBar.visibility = View.GONE
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val nomorSuratTerpilih = viewModel.daftarSurat.value?.get(position)?.nomor ?: return@setOnItemClickListener
            pilihSurat(nomorSuratTerpilih)
        }
    }
    private fun pilihSurat(nomorSurat: Int) {
        val intent = Intent().apply {
            putExtra("nomorSurat", nomorSurat)
        }
        targetFragment?.onActivityResult(targetRequestCode, RESULT_OK, intent)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}