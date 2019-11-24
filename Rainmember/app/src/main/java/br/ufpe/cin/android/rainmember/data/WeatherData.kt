package br.ufpe.cin.android.rainmember.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "weatherData")
class WeatherData (
    val temperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val humidity: Double,
    val condition: String,
    val currentUv: Double,
    val maxUv : Double,
    @PrimaryKey val datetime: Date
) {
    override fun toString(): String {
        return "Temperature: $temperature | Max: $maxTemperature | Min: $minTemperature | Humidity: $humidity | Condition: $condition | Current UV: $currentUv | Max UV: $maxUv"
    }
}

