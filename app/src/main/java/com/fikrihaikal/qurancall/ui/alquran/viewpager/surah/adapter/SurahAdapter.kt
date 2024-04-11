package com.fikrihaikal.qurancall.ui.alquran.viewpager.surah.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ListItemSurahBinding.inflate
import com.fikrihaikal.qurancall.databinding.ListItemSurahBinding
import com.fikrihaikal.qurancall.network.model.response.surah.DataItem
import com.fikrihaikal.qurancall.utils.Constant

class SurahAdapter: RecyclerView.Adapter<SurahAdapter.ListViewHolder>(){

    private val callback = object : DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean =
            oldItem.nomor == newItem.nomor

        override fun areContentsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this,callback)

    inner class ListViewHolder(private val binding: ListItemSurahBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(item:DataItem){
                    binding.apply {
                        tvNoSurah.text = item.nomor.toString()
                        tvNameSurah.text = item.namaLatin
                        tvArtiSurah.text = item.arti
                        binding.root.setOnClickListener {
                            val bundle = Bundle().apply {
                                putString(Constant.KEY_SURAH,item.nomor.toString())
                            }
                            it.findNavController().navigate(R.id.detailSurahFragment,bundle)
                            Log.d("kirimIdSurah",bundle.toString())
                        }
                    }
                }
            }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])
}