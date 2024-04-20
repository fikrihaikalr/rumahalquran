package com.fikrihaikal.qurancall.ui.detailsurah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ItemListAyatBinding
import com.fikrihaikal.qurancall.network.model.response.detailsurah.AyahDataItem

class DetailSurahAdapter : RecyclerView.Adapter<DetailSurahAdapter.ListViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<AyahDataItem>() {
        override fun areItemsTheSame(
            oldItem: AyahDataItem,
            newItem: AyahDataItem
        ): Boolean =
            oldItem.ayah == newItem.ayah

        override fun areContentsTheSame(
            oldItem: AyahDataItem,
            newItem: AyahDataItem
        ): Boolean =
            oldItem == newItem
    }
    val differ = AsyncListDiffer(this, callback)

    inner class ListViewHolder(private val binding: ItemListAyatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AyahDataItem) {
            binding.tvNoAyat.text = item.ayah
            binding.tvArabAyat.text = item.arab
            binding.tvLatinAyat.text = item.latin
            binding.tvArtiAyat.text = item.text
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(
            ItemListAyatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

}