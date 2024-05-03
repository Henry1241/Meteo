package mx.itson.meteo

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mx.itson.meteo.entities.Location
import mx.itson.meteo.utils.RetrofitUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    var context : Context = this
    var map : GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getLocation("27.9681409", "-110.9189332")
    }
    fun showWeatherDialog(temperature: String, elevation: String, windSpeed: String, windDirection: String, weatherCode: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_weather)

        val txtViewTemperature = dialog.findViewById<TextView>(R.id.txtViewTemperatureValue)
        txtViewTemperature.text = temperature

        val txtViewWindSpeed = dialog.findViewById<TextView>(R.id.txtViewWindspeedValue)
        txtViewWindSpeed.text = windSpeed

        val txtViewWindDirection = dialog.findViewById<TextView>(R.id.txtViewWindDirectionValue)
        txtViewWindDirection.text = windDirection

        val txtViewElevation = dialog.findViewById<TextView>(R.id.txtViewElevationValue)
        txtViewElevation.text = elevation

        val imageViewWeatherCode = dialog.findViewById<ImageView>(R.id.imgViewWeatherCode)
        val backViewWeatherCode = dialog.findViewById<RelativeLayout>(R.id.weather)

        val txtViewWeather = dialog.findViewById<TextView>(R.id.txtViewWeather)
        txtViewWeather.text = weatherCode.toString()

        if (weatherCode == 0){
            txtViewWeather.setText(getText(R.string.sunny))
            imageViewWeatherCode.setImageResource(R.drawable.sun)
            backViewWeatherCode.setBackgroundResource(R.drawable.sunny)
        }else if (weatherCode in 1..3){
            txtViewWeather.setText(getText(R.string.cloudy))
            imageViewWeatherCode.setImageResource(R.drawable.cloud)
            backViewWeatherCode.setBackgroundResource(R.drawable.cloudy)
        }else if (weatherCode in 14..16){
            txtViewWeather.setText(getText(R.string.precipitation))
            imageViewWeatherCode.setImageResource(R.drawable.parcialrain)
            backViewWeatherCode.setBackgroundResource(R.drawable.precipitation)
        }else if (weatherCode in 25..27  || weatherCode in 70 .. 79 || weatherCode in 85 .. 86){
            txtViewWeather.setText(getText(R.string.snow))
            imageViewWeatherCode.setImageResource(R.drawable.snowy)
            backViewWeatherCode.setBackgroundResource(R.drawable.snow)
        }else if (weatherCode in 60..69 || weatherCode in 80..83){
            txtViewWeather.setText(getText(R.string.rain))
            imageViewWeatherCode.setImageResource(R.drawable.rainy)
            backViewWeatherCode.setBackgroundResource(R.drawable.rain)
        }else if (weatherCode in 95..99) {
            txtViewWeather.setText(getText(R.string.storm))
            imageViewWeatherCode.setImageResource(R.drawable.stormtime)
            backViewWeatherCode.setBackgroundResource(R.drawable.storm)
        }

        val btnClose = dialog.findViewById<Button>(R.id.button_close)
        btnClose.setOnClickListener() {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun getLocation(lat: String, lon: String){
        val call : Call<Location> =
            RetrofitUtils.getApi()!!.getLocation(lat, lon,
                true)
        call.enqueue(object : Callback<Location> {
            override fun onResponse(call: Call<Location>, response: Response<Location>) {

                try {
                    val location: Location? = response.body()
                    /*
                    Toast.makeText(
                        context, "La altitud es " +
                                location!!.elevation.toString() + "la velocidad del viento es "
                                + "la temperatura es " + location.weather!!.temperature.toString() +
                                location.unit!!.temperature.toString() +
                                "la direccion del viento es " + location.weather!!.windDirection.toString() +
                                location.unit!!.windDirection.toString()
                                + "la velocidad del viento es" + location.weather!!.windSpeed.toString() +
                                location.unit!!.windSpeed.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                     */
                    val temperature = "${location!!.weather!!.temperature} ${location.unit!!.temperature}"
                    val elevation = "${location.elevation}"
                    val windSpeed = "${location.weather!!.windSpeed} ${location.unit!!.windSpeed}"
                    val windDirection = "${location.weather!!.windDirection} ${location.unit!!.windDirection}"
                    val weatherCode = location.weather!!.weatherCode

                    if (weatherCode != null){
                        showWeatherDialog(temperature, elevation, windSpeed, windDirection, weatherCode)
                    }

                }catch (e: Exception){
                    Log.e("error", e.message.toString())
                    
                }
            }

            override fun onFailure(call: Call<Location>, t: Throwable) {
                Log.e("error", t.message.toString())
            }

        } )
    }

 

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_close->closeOptionsMenu()
            }
        }
    }