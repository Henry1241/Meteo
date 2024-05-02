package mx.itson.meteo.entities

import com.google.gson.annotations.SerializedName

class Unit {

    var temperature : String? = null
    @SerializedName("windspeed")
    var windSpeed : String? = null
    @SerializedName("winddirection")
    var windDirection : String? = null
    @SerializedName("current_weather_units")
    var weatherUnit: Weather? = null
}