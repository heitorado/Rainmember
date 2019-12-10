package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data

import androidx.room.*
import java.sql.Time

@Entity(tableName = "alarm")
class Alarm (
    var active: Boolean,
    var alarmDates: Int,
    @PrimaryKey
    var alarmTime: String //HH:MM format
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

    fun weekDaysArray() : ArrayList<Int> {
        var weekdays = ArrayList<Int>(7)

        // Monday
        if ((1 and alarmDates) != 0) weekdays.add(2)

        // Tuesday
        if ((2 and alarmDates) != 0) weekdays.add(3)

        // Wednesday
        if ((4 and alarmDates) != 0) weekdays.add(4)

        // Thursday
        if ((8 and alarmDates) != 0) weekdays.add(5)

        // Friday
        if ((16 and alarmDates) != 0) weekdays.add(6)

        // Saturday
        if ((32 and alarmDates) != 0) weekdays.add(7)

        // Sunday
        if ((64 and alarmDates) != 0) weekdays.add(1)

        return weekdays
    }
}
