package com.fikrihaikal.qurancall.ui.user.pilihguru.adapter

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ListGuruBinding.inflate
import com.fikrihaikal.qurancall.databinding.ListGuruBinding
import com.fikrihaikal.qurancall.network.model.response.guru.DataItem
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.utils.Constant


//class GuruAdapter(private val itemClick:(DataItem) -> Unit) : RecyclerView.Adapter<GuruAdapter.GuruViewHolder>(){
//
//    class GuruViewHolder(private val binding: ListGuruBinding, val itemClick:(DataItem) -> Unit) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bindView(item: DataItem){
//            with(item){
//                itemView.setOnClickListener { itemClick(this) }
//                binding.run {
//                    tvNamaGuru.text = item.username
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
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruViewHolder {
//        val binding = ListGuruBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return GuruViewHolder(binding, itemClick)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: GuruViewHolder, position: Int) {
//        holder.bindView(items[position])
//    }
//}
//class GuruAdapter(
//    private val itemClick: (DataItem) -> Unit
//) : RecyclerView.Adapter<GuruAdapter.ViewHolder>() {
//
//    private val differ = AsyncListDiffer(this,
//        object : DiffUtil.ItemCallback<DataItem>() {
//            override fun areItemsTheSame(
//                oldItem: DataItem,
//                newItem: DataItem
//            ): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(
//                oldItem: DataItem,
//                newItem: DataItem
//            ): Boolean {
//                return oldItem.id == newItem.id
//            }
//        })
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): GuruAdapter.ViewHolder =
//        ViewHolder(
//            ListGuruBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//
//
//    override fun onBindViewHolder(holder: GuruAdapter.ViewHolder, position: Int) {
//        holder.bind(differ.currentList[position])
//    }
//
//    override fun getItemCount(): Int = differ.currentList.size
//
//    inner class ViewHolder(private val binding: ListGuruBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(guru: DataItem) {
//            with(binding) {
//                tvNamaGuru.text = guru.username
//            }
//            itemView.setOnClickListener {
//                val action = PilihGuruFragmentDirections.actionPilihGuruFragmentToDetailGuruFragment(guru.id)
//                it.findNavController().navigate(action)
//            }
//        }
//    }
//
//    fun setData(data: List<DataItem>) {
//        differ.submitList(data)
//    }
//}

//class GuruAdapter : RecyclerView.Adapter<GuruAdapter.ListViewHolder>() {
//    private val callback = object : DiffUtil.ItemCallback<DataItem>() {
//        override fun areItemsTheSame(
//            oldItem: DataItem,
//            newItem: DataItem
//        ): Boolean =
//            oldItem.id == newItem.id
//
//        override fun areContentsTheSame(
//            oldItem: DataItem,
//            newItem: DataItem
//        ): Boolean =
//            oldItem == newItem
//    }
//
//    val differ = AsyncListDiffer(this, callback)
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): ListViewHolder =
//        ListViewHolder(ListGuruBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
//        holder.bind(differ.currentList[position])
//
//    override fun getItemCount(): Int = differ.currentList.size
//
//    inner class ListViewHolder(private val binding: ListGuruBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: DataItem) {
//            binding.tvNamaGuru.text = item.username
//            binding.root.setOnClickListener {
//                it.findNavController()
//                    .navigate(
//                        R.id.action_pilihGuruFragment_to_detailGuruFragment,
//                        Bundle().apply { putString("GURU_ID", item.id) })
//            }
//        }
//    }
//}

class GuruAdapter : RecyclerView.Adapter<GuruAdapter.ListViewHolder>() {

    private val callback = object: DiffUtil.ItemCallback<DataItem>(){
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

    inner class ListViewHolder(private val binding: ListGuruBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: DataItem){
                binding.tvNamaGuru.text = item.username
                binding.root.setOnClickListener {
                    val bundle =Bundle().apply {
                        putString(Constant.KEY_USERNAME,item.id)
                    }
                    it.findNavController().navigate(R.id.detailGuruFragment,bundle)
                    Log.d("kirimId",bundle.toString())
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

