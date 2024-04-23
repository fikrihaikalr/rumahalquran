package com.fikrihaikal.qurancall.ui.user.alquran.viewpager.surah.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ListItemSurahBinding.inflate
import com.fikrihaikal.qurancall.databinding.ListItemSurahBinding
import com.fikrihaikal.qurancall.network.model.response.surah.DataItem
import com.fikrihaikal.qurancall.utils.Constant
import java.util.Locale

class SurahAdapter: RecyclerView.Adapter<SurahAdapter.ListViewHolder>(), Filterable {

    private val callback = object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, callback)
    var surahListFull: List<DataItem> = ArrayList()
    inner class ListViewHolder(private val binding: ListItemSurahBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataItem) {
            binding.tvNoSurah.text = item.number
            binding.tvNameSurah.text = item.surahName
            binding.tvArtiSurah.text = item.translateId
            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(Constant.KEY_SURAH, item.id)
                }
                it.findNavController().navigate(R.id.detailSurahFragment, bundle)
                Log.d("kirimIdSurah", bundle.toString())
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getFilter(): Filter {
        return surahFilter
    }

    private val surahFilter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<DataItem>()

            if (constraint.isNullOrEmpty()){
                filteredList.addAll(surahListFull)
            }else{
                val filterPattern = constraint.toString().toLowerCase().trim()

                for (surah in surahListFull){
                    if (surah.surahName.toLowerCase().contains(filterPattern)){
                        filteredList.add(surah)
                    }
                }
            }
            val result = FilterResults()
            result.values = filteredList
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            differ.submitList(results?.values as List<DataItem>)
        }
    }

    fun submitList(list: List<DataItem>){
        surahListFull = ArrayList(list)
        differ.submitList(list)
    }
}