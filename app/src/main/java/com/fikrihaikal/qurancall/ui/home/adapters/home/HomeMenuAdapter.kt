package com.fikrihaikal.qurancall.ui.home.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListMenuBinding
import com.fikrihaikal.qurancall.network.model.data.home.ItemMenu

class HomeMenuAdapter(private val itemClick:(ItemMenu) -> Unit) : RecyclerView.Adapter<HomeMenuAdapter.MenuViewHolder>(){

    class MenuViewHolder(private val binding: ListMenuBinding, val itemClick:(ItemMenu) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindView(item: ItemMenu){
                with(item){
                    itemView.setOnClickListener { itemClick(this) }
                    binding.run {
                        tvName.text = name
                        imgMenu.setImageResource(img)
                    }
                }
            }

    }

    private var items: MutableList<ItemMenu> = mutableListOf()

    fun setItems(items:List<ItemMenu>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun addItems(items: List<ItemMenu>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}