package com.example.weather.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Weather
import com.squareup.picasso.Picasso

class rvAdapter(val listOfWeather:List<Weather>):RecyclerView.Adapter<rvAdapter.ViewHolder>() {

    inner class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

        fun onBind(weather: Weather){
            val city = itemView.findViewById<TextView>(R.id.tv_city)
            val date = itemView.findViewById<TextView>(R.id.tv_date)
            val temp = itemView.findViewById<TextView>(R.id.tv_temp)
            val type = itemView.findViewById<TextView>(R.id.type)
            val icon = itemView.findViewById<ImageView>(R.id.imgIcon)
            city.text = weather.city
            date.text = weather.date
            temp.text = weather.temp
            type.text = weather.type
         //   icon.setImageResource(weather.icon.toInt())
            Picasso.get().load(weather.icon).into(icon)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfWeather.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listOfWeather[position])

    }
}