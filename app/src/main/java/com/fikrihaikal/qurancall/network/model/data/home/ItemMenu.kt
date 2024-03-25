package com.fikrihaikal.qurancall.network.model.data.home

import android.content.Context
import androidx.annotation.DrawableRes
import com.fikrihaikal.qurancall.R

data class ItemMenu(

    val name:String,
    @DrawableRes
    val img:Int,
    val navigation:Int
)

fun getItemMenuList(context:Context):List<ItemMenu>{
    val listMenu = mutableListOf<ItemMenu>()
    listMenu.clear()
    listMenu.add(
        ItemMenu(
            context.getString(R.string.study_materials),
            R.drawable.ic_materi_belajar,
            R.id.action_homeFragment_to_materiBelajarFragment
        )
    )
    listMenu.add(
        ItemMenu(
            context.getString(R.string.collection_of_prayers),
            R.drawable.ic_kumpulan_doa,
            R.id.action_homeFragment_to_materiBelajarFragment
        )
    )
    listMenu.add(
        ItemMenu(
            context.getString(R.string.setor_hafalan),
            R.drawable.ic_setor_hafalan,
            R.id.action_homeFragment_to_materiBelajarFragment
        )
    )
    listMenu.add(
        ItemMenu(
            context.getString(R.string.sambung_ayat),
            R.drawable.ic_sambung_ayat,
            R.id.action_homeFragment_to_materiBelajarFragment
        )
    )
    return listMenu
}