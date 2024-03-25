package com.fikrihaikal.qurancall.network.model.data.home

import android.content.Context
import com.fikrihaikal.qurancall.R

data class ItemDoa(
    val nameDoa:String,
    val doa: String,
    val artiDoa: String
)

fun getItemDoaList(context:Context):List<ItemDoa>{
    val listDoa = mutableListOf<ItemDoa>()
    listDoa.clear()
    listDoa.add(
        ItemDoa(
            context.getString(R.string.prayer_before_sleeping),
            "بِاسْمِ اللهِ اللَّهُمَّ إِنِّي أَعُوْذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَاءِثِ",
            context.getString(R.string.with_your_name_ya_allah_I_live_and_die)
        )
    )
    listDoa.add(
        ItemDoa(
            context.getString(R.string.prayer_before_sleeping),
            "بِاسْمِ اللهِ اللَّهُمَّ إِنِّي أَعُوْذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَاءِثِ",
            context.getString(R.string.with_your_name_ya_allah_I_live_and_die)
        )
    )
    listDoa.add(
        ItemDoa(
            context.getString(R.string.prayer_before_sleeping),
            "بِاسْمِ اللهِ اللَّهُمَّ إِنِّي أَعُوْذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَاءِثِ",
            context.getString(R.string.with_your_name_ya_allah_I_live_and_die)
        )
    )
    listDoa.add(
        ItemDoa(
            context.getString(R.string.prayer_before_sleeping),
            "بِاسْمِ اللهِ اللَّهُمَّ إِنِّي أَعُوْذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَاءِثِ",
            context.getString(R.string.with_your_name_ya_allah_I_live_and_die)
        )
    )
    return listDoa
}