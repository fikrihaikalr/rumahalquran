package com.fikrihaikal.qurancall.ui.home.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListDoaBinding
import com.fikrihaikal.qurancall.network.model.data.home.ItemDoa

class ListDoaAdapter : RecyclerView.Adapter<ListDoaAdapter.ListDoaViewHolder>(){

    private var items: MutableList<ItemDoa> = mutableListOf()

    class ListDoaViewHolder(private val binding: ListDoaBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bindView(item: ItemDoa){
            with(item){
                binding.run {
                    tvNameDoa.text = nameDoa
                    tvDoa.text = doa
                    tvArti.text = artiDoa
                }
            }
        }
    }

    fun setListDoa(items:List<ItemDoa>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDoaViewHolder {
        val binding = ListDoaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListDoaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListDoaAdapter.ListDoaViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

}