package com.fikrihaikal.qurancall.ui.teacher.materibelajar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListItemMenuMateriBinding.inflate
import com.fikrihaikal.qurancall.databinding.ListItemMenuMateriBinding
import com.fikrihaikal.qurancall.network.model.response.menumateri.DataItem

class MenuMateriTeacherAdapter: RecyclerView.Adapter<MenuMateriTeacherAdapter.ListViewHolder>() {

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

    inner class ListViewHolder(private val binding: ListItemMenuMateriBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(item: DataItem){
                    binding.tvTitle.text = item.title
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