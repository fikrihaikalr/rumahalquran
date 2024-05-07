package com.fikrihaikal.qurancall.ui.user.listsubmateri.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListItemSubMenuMateriBinding
import com.fikrihaikal.qurancall.network.model.response.submenumateri.DataItem


class SubMenuMateriAdapter(private val itemClick: (DataItem) -> Unit) :
    RecyclerView.Adapter<SubMenuMateriAdapter.InnerViewHolder>() {

    class InnerViewHolder(
        private val binding: ListItemSubMenuMateriBinding,
        val itemClick: (DataItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: DataItem) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvTitle.text = item.title
                    tvAuthor.text = item.author
                }
            }
        }
    }

    private var items: MutableList<DataItem> = mutableListOf()

    fun setItems(items: List<DataItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding =
            ListItemSubMenuMateriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}