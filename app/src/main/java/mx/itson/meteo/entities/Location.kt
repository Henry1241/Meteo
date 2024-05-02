package mx.itson.meteo.entities

import com.google.gson.annotations.SerializedName

class Location {

    var latitude : Float? = null
    var longitude : Float? = null
    var elevation : Float? = null

    @SerializedName("current_weather")
    var weather: Weather? = null
    @SerializedName("current_weather_units")
    var unit : Unit? = null
}