package mx.itson.meteo.interfaces

import android.health.connect.datatypes.units.Temperature
import mx.itson.meteo.entities.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteo {

    @GET("forecast")
    fun getLocation(@Query("latitude") latitude: String,
                    @Query("longitude") longitude: String,
                    @Query("current_weather") currentWeather: Boolean )
                    : Call<Location>
}