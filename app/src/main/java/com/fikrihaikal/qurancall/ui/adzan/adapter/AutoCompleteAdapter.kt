package com.fikrihaikal.qurancall.ui.adzan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fikrihaikal.qurancall.network.model.response.adzan.KotaResponse

class AutoCompleteAdapter(
    context: Context,
    private var cities: List<KotaResponse>
): ArrayAdapter<KotaResponse>(context,android.R.layout.simple_dropdown_item_1line,cities){

    fun setData(cities: List<KotaResponse>){
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return cities.size
    }

    override fun getItem(position: Int): KotaResponse? {
        return cities[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line,parent,false)
        }

        val city = cities[position]
        view?.findViewById<TextView>(android.R.id.text1)?.text = city.lokasi

        return view!!
    }
}