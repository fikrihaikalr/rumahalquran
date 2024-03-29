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
            context.getString(R.string.wake_up_prayer),
            "اَلْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُوْرُ",
            context.getString(R.string.the_meaning_of_the_wake_up_prayer)
        )
    )
    listDoa.add(
        ItemDoa(
            context.getString(R.string.meal_prayer),
            "اللَّهُمَّ بَارِكْ لَنَا فِيْمَا رَزَقْتَنَا وَقِنَا عَذَابَ النَّارِ",
            context.getString(R.string.meaning_of_meal_prayer)
        )
    )
    listDoa.add(
        ItemDoa(
            context.getString(R.string.prayer_after_eating),
            "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مُسْلِمِيْنَ",
            context.getString(R.string.the_meaning_of_prayer_after_eating)
        )
    )
    return listDoa
}