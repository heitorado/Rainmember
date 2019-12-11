package br.ufpe.cin.android.rainmember.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "weatherData")
class WeatherData (
    val cityName: String,
    val temperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val humidity: Double,
    val condition: String,
    val weatherCode: Int,
    val currentUv: Double,
    val maxUv : Double,
    var dataTag: String,
    @PrimaryKey val datetime: Date
) {
    override fun toString(): String {
        return "City: $cityName | Temperature: $temperature | Max: $maxTemperature | Min: $minTemperature | Humidity: $humidity | Condition: $condition | Weather Code $weatherCode | Current UV: $currentUv | Max UV: $maxUv"
    }
}

