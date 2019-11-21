package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room

import androidx.room.TypeConverter
import java.sql.Time
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
