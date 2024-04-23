package com.fikrihaikal.qurancall.network.model.data.home

import android.content.Context
import androidx.annotation.DrawableRes
import com.fikrihaikal.qurancall.R

data class ItemMenuTeacher(

    val name:String,
    @DrawableRes
    val img:Int,
    val navigation:Int
)
fun getItemMenuTeacherList(context: Context):List<ItemMenuTeacher>{
    val listMenuTeacher = mutableListOf<ItemMenuTeacher>()
    listMenuTeacher.clear()
    listMenuTeacher.add(
        ItemMenuTeacher(
            context.getString(R.string.study_materials),
            R.drawable.ic_materi_belajar,
            R.id.action_homeTeacherFragment_to_materiBelajarTeacherFragment
        )
    )
    listMenuTeacher.add(
        ItemMenuTeacher(
            context.getString(R.string.collection_of_prayers),
            R.drawable.ic_kumpulan_doa,
            R.id.action_homeTeacherFragment_to_materiBelajarTeacherFragment
        )
    )
    listMenuTeacher.add(
        ItemMenuTeacher(
            context.getString(R.string.add_category),
            R.drawable.ic_tambah_kategori,
            R.id.action_homeTeacherFragment_to_materiBelajarTeacherFragment
        )
    )
    listMenuTeacher.add(
        ItemMenuTeacher(
            context.getString(R.string.upload_material),
            R.drawable.ic_unggah_materi,
            R.id.action_homeTeacherFragment_to_materiBelajarTeacherFragment
        )
    )

    return listMenuTeacher
}