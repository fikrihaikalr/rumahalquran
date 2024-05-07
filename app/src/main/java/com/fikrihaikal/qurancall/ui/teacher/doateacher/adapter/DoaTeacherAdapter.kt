package com.fikrihaikal.qurancall.ui.teacher.doateacher.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ListDoaBinding
import com.fikrihaikal.qurancall.network.model.response.doa.DataItem
import com.fikrihaikal.qurancall.utils.Constant

class DoaTeacherAdapter : RecyclerView.Adapter<DoaTeacherAdapter.ListViewHolder>(){
    private val callback = object : DiffUtil.ItemCallback<DataItem>(){
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
    val differ = AsyncListDiffer(this,callback)

    inner class ListViewHolder(private val binding: ListDoaBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind (item: DataItem){
            binding.tvDoa.text = item.doaName
            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString(Constant.KEY_DOA,item.id)
                }
                it.findNavController().navigate(R.id.detailDoaTeacherFragment,bundle)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(ListDoaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])
}