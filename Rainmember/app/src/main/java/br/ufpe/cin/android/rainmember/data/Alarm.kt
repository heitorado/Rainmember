package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data

import androidx.room.*
import java.sql.Time

@Entity(tableName = "alarm")
class Alarm (
    val active: Boolean,
    val alarmTime: Time,
    val alarmDates: Int,
    @PrimaryKey(autoGenerate = true) val id: Int
)