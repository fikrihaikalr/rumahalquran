package com.fikrihaikal.qurancall.ui.teacher.hometeacher.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListMenuTeacherBinding
import com.fikrihaikal.qurancall.network.model.data.home.ItemMenu
import com.fikrihaikal.qurancall.network.model.data.home.ItemMenuTeacher

class HomeMenuTeacherAdapter(private val itemClick:(ItemMenuTeacher) -> Unit) : RecyclerView.Adapter<HomeMenuTeacherAdapter.MenuViewHolder>(){

    class MenuViewHolder(private val binding: ListMenuTeacherBinding, val itemClick:(ItemMenuTeacher) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ItemMenuTeacher){
            with(item){
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvName.text = name
                    imgMenu.setImageResource(img)
                }
            }
        }

    }

    private var items: MutableList<ItemMenuTeacher> = mutableListOf()

    fun setItems(items:List<ItemMenuTeacher>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    fun addItems(items: List<ItemMenuTeacher>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ListMenuTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}