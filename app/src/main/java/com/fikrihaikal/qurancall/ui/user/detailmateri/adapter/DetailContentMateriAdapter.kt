package com.fikrihaikal.qurancall.ui.user.detailmateri.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListItemContentMateriBinding
import com.fikrihaikal.qurancall.network.model.response.detailcontentmateri.Data

class DetailContentMateriAdapter(private val itemClick: (Data) -> Unit) :
    RecyclerView.Adapter<DetailContentMateriAdapter.InnerViewHolder>() {

    class InnerViewHolder(
        private val binding: ListItemContentMateriBinding,
        val itemClick: (Data) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Data) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvTitle.text = item.title
                    tvAuthor.text = item.author
                }
            }
        }
    }

    private var items: MutableList<Data> = mutableListOf()

    fun setItems(items: List<Data>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding =
            ListItemContentMateriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}