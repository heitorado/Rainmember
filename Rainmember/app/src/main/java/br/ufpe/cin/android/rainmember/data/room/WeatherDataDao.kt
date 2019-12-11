package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.ufpe.cin.android.rainmember.data.WeatherData

@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWeatherData (vararg weatherDataObjects: WeatherData)

    @Query("SELECT * FROM weatherData")
    fun getAll(): Array<WeatherData>

    @Query("SELECT * FROM weatherData WHERE dataTag LIKE 'CURRENT' ORDER BY datetime DESC LIMIT 1")
    fun getLatest() : WeatherData?

    @Query("SELECT * FROM weatherData WHERE dataTag LIKE 'COMPARISON' ORDER BY datetime DESC LIMIT 1")
    fun getLatestComparation() : WeatherData?
}