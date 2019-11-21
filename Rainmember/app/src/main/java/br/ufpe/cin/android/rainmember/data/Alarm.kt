package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data

import androidx.room.*
import java.sql.Time

@Entity(tableName = "alarm")
class Alarm (
    val active: Boolean,
    val alarmTime: String, //HH:MM format
    val alarmDates: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int
) {
    fun alarmDays(): String {
        var weekdays = ArrayList<String>(7)

        // Monday
        if ((1 and alarmDates) != 0) weekdays.add("mon")

        // Tuesday
        if ((2 and alarmDates) != 0) weekdays.add("tue")

        // Wednesday
        if ((4 and alarmDates) != 0) weekdays.add("wed")

        // Thursday
        if ((8 and alarmDates) != 0) weekdays.add("thu")

        // Friday
        if ((16 and alarmDates) != 0) weekdays.add("fri")

        // Saturday
        if ((32 and alarmDates) != 0) weekdays.add("sat")

        // Sunday
        if ((64 and alarmDates) != 0) weekdays.add("sun")

        return weekdays.joinToString(separator = ", ")
    }
}
