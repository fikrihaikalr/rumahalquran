package com.fikrihaikal.qurancall.ui.user.doa.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ListDoaBinding.inflate
import com.fikrihaikal.qurancall.databinding.ListDoaBinding
import com.fikrihaikal.qurancall.network.model.response.doa.DataItem
import com.fikrihaikal.qurancall.utils.Constant


class DoaAdapter : RecyclerView.Adapter<DoaAdapter.ListViewHolder>(){
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
                        it.findNavController().navigate(R.id.detailDoaFragment,bundle)
                    }
                }
            }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])
}

//class DoaAdapter :
//    PagingDataAdapter<DataItem, DoaAdapter.ViewHolder>(callback){
//
//    companion object {
//        val callback = object : DiffUtil.ItemCallback<DataItem>() {
//            override fun areItemsTheSame(
//                oldItem: DataItem,
//                newItem: DataItem
//            ): Boolean =
//                oldItem.id == newItem.id
//
//            override fun areContentsTheSame(
//                oldItem: DataItem,
//                newItem: DataItem
//            ): Boolean =
//                oldItem == newItem
//        }
//    }
//
//    class ViewHolder(private val binding: ListDoaBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: DataItem) {
//            binding.apply {
//                tvDoa.text = item.doaName
//            }
//            itemView.setOnClickListener {
//                val action = DoaFragmentDirections.actionDoaFragmentToDetailDoaFragment(item.id)
//                it.findNavController().navigate(action)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        ViewHolder(inflate(LayoutInflater.from(parent.context), parent, false))
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = getItem(position)
//        if (data!=null) holder.bind(data)
//    }
//}


//class DoaAdapter(private val itemClick:(DataItem) -> Unit) : RecyclerView.Adapter<DoaAdapter.MenuViewHolder>(){
//
//    class MenuViewHolder(private val binding: ListDoaBinding, val itemClick:(DataItem) -> Unit) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bindView(item: DataItem){
//            with(item){
//                itemView.setOnClickListener { itemClick(this) }
//                binding.run {
//                    tvDoa.text = item.doaName
//                }
//            }
//        }
//
//    }
//
//    private var items: MutableList<DataItem> = mutableListOf()
//
//    fun setItems(items:List<DataItem>){
//        this.items.clear()
//        this.items.addAll(items)
//        notifyDataSetChanged()
//    }
//    fun addItems(items: List<DataItem>) {
//        this.items.addAll(items)
//        notifyDataSetChanged()
//    }
//
//    fun clearItems() {
//        this.items.clear()
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
//        val binding = ListDoaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MenuViewHolder(binding, itemClick)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
//        holder.bindView(items[position])
//    }
//}