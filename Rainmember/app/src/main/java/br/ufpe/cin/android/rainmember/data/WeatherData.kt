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
    @PrimaryKey val datetime: Date
)

