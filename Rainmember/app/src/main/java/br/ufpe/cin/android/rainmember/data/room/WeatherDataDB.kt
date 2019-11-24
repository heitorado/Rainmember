package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room

import android.content.Context
import androidx.room.*
import br.ufpe.cin.android.rainmember.data.WeatherData

@Database(entities = [WeatherData::class], version = 2)
@TypeConverters(Converters::class)
abstract class WeatherDataDB : RoomDatabase() {
    abstract fun weatherDataDAO(): WeatherDataDao

    companion object {
        private var INSTANCE: WeatherDataDB? = null

        fun getDatabase(ctx: Context): WeatherDataDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        WeatherDataDB::class.java,
                        "weatherData.db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                }
            }
            return INSTANCE!!
        }

    }

}
