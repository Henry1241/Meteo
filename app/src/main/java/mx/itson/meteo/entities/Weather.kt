package mx.itson.meteo.entities

import com.google.gson.annotations.SerializedName


class Weather {

    var temperature : Float? = null
    @SerializedName("windspeed")
    var windSpeed : Float? = null
    @SerializedName("winddirection")
    var windDirection : Float? = null
    @SerializedName("weathercode")
    var weatherCode : Int?  =  null
}