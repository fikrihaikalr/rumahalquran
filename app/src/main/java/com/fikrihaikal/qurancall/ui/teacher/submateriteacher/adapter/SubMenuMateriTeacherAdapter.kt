package com.fikrihaikal.qurancall.ui.teacher.submateriteacher.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.databinding.ListItemSubMenuMateriTeacherBinding
import com.fikrihaikal.qurancall.network.model.response.submenumateri.DataItem

class SubMenuMateriTeacherAdapter(private val itemClick: (DataItem) -> Unit,
    private val deleteClick:(DataItem) -> Unit) :
    RecyclerView.Adapter<SubMenuMateriTeacherAdapter.InnerViewHolder>() {

    class InnerViewHolder(
        private val binding: ListItemSubMenuMateriTeacherBinding,
        val itemClick: (DataItem) -> Unit,
        val deleteClick: (DataItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: DataItem) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvTitle.text = item.title
                    tvAuthor.text = item.author
                    icDelete.setOnClickListener { showDeleteConfirmationDialog(item) }
                }
            }
        }
        private fun showDeleteConfirmationDialog(item:DataItem){
            AlertDialog.Builder(binding.root.context)
                .setMessage("Apakah Anda yakin ingin menghapus sub-materi ini?")
                .setPositiveButton("Ya"){_,_ ->
                    deleteClick(item)
                }
                .setNegativeButton("Tidak",null)
                .show()
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
            ListItemSubMenuMateriTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding, itemClick,deleteClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.bindView(items[position])
    }
}