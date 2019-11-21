package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm

@Database(entities = [Alarm::class], version = 2)
abstract class AlarmDB : RoomDatabase() {
    abstract fun alarmDAO(): AlarmDao

    companion object {
        private var INSTANCE: AlarmDB? = null

        fun getDatabase(ctx: Context): AlarmDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        AlarmDB::class.java,
                        "alarm.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }

}