package com.example.weather

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.VelocityTracker
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.model.Weather
import com.example.weather.rvAdapter.rvAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    lateinit var rvAdapter:rvAdapter
    lateinit var weatherList: MutableList<Weather>

     var countryList: MutableList<String> = mutableListOf("Namangan","Farg'ona","Andijon","Toshkent","Jizzax","Buxoro","Samarqand","Surxondaryo","Sirdaryo","Qoraqalpog'iston","Xorazm","Navoiy")

    var country = "Tashkent"
    var api_key = "f70531640cd6706f7691a45f80c0225b"

    var requestQueue:RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherList = ArrayList()






        for (i in 0 until countryList.size){
            getWeather(countryList[i])
        }




    }

    private fun getWeather(country: String) {
     val URL = "https://api.openweathermap.org/data/2.5/weather?q=$country&appid=$api_key"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,URL,null, Response.Listener {
                response ->
            try {
                val mainObject = response!!.getJSONObject("main")
                 val weatherOBject =  response!!.getJSONArray("weather")
                val jsonObject = weatherOBject.getJSONObject(0)
                val resTemp = mainObject.getDouble("temp").toString()
                val resDescription = jsonObject.getString("description")
                val resIcon = jsonObject.getString("icon")
                val resCity = response.getString("name")
                 val iconUrl = "https://openweathermap.org/img/wn/$resIcon.png"

                //Converting temperatura into celsie
                val tempInt = resTemp.toDouble()
                var celsie = tempInt - 273.15
                celsie = celsie.roundToInt().toDouble()

                //Initialise respone values with UI
                //Date
                val calendar:Calendar = Calendar.getInstance()
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
                var formattedDate = simpleDateFormat.format(calendar.time)
              Log.d("TAG", "getWeather: $celsie")

                weatherList.add(Weather(resCity,formattedDate,celsie.toString(),resDescription,iconUrl))
                Log.d("List", "getWeather: $weatherList")

                rvAdapter = rvAdapter(weatherList)
                recyclerView.adapter = rvAdapter






            }catch (e:JSONException){
            e.printStackTrace()
            }

        }, Response.ErrorListener {
                error ->
            error.printStackTrace()
        })

        requestQueue = Volley.newRequestQueue(this)
        requestQueue?.add(jsonObjectRequest)


    }

}