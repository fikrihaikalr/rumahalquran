package com.fikrihaikal.qurancall.ui.teacher.materibelajar.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.qurancall.R
import com.fikrihaikal.qurancall.databinding.ListItemMenuMateriTeacherBinding.inflate
import com.fikrihaikal.qurancall.databinding.ListItemMenuMateriTeacherBinding
import com.fikrihaikal.qurancall.network.model.response.menumateri.DataItem
import com.fikrihaikal.qurancall.utils.Constant

class MenuMateriTeacherAdapter(private val onDeleteClickListener: (String) -> Unit):
    RecyclerView.Adapter<MenuMateriTeacherAdapter.ListViewHolder>() {

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

    inner class ListViewHolder(private val binding: ListItemMenuMateriTeacherBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(item: DataItem){
                    binding.tvTitle.text = item.title
                    val jumlahMateri = item.jumlahMateri.toInt()
                    binding.tvCount.text = "($jumlahMateri)"
                    binding.cvMateri.setOnClickListener {
                        val bundle = Bundle().apply {
                            putString(Constant.KEY_MENU_MATERI,item.id)
                        }
                        it.findNavController().navigate(R.id.subMenuMateriTeacherFragment,bundle)
                        Log.d("kirimId",bundle.toString())
                    }
                    binding.icDelete.setOnClickListener {
                        if (jumlahMateri > 0){
                            Toast.makeText(binding.root.context,"Materi tidak bisa dihapus karena masih terdapat Sub Materi",Toast.LENGTH_SHORT).show()
                        }else if (jumlahMateri == 0){
                            showDeleteConfirmationDialog(item.id)
                        }

                    }
                }
                private fun showDeleteConfirmationDialog(materiId:String){
                    AlertDialog.Builder(binding.root.context)
                        .setMessage("Apakah Anda yakin ingin menghapus kategori ini?")
                        .setPositiveButton("Ya"){ _,_ ->
                            onDeleteClickListener.invoke(materiId)
                        }
                        .setNegativeButton("Tidak", null)
                        .show()
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