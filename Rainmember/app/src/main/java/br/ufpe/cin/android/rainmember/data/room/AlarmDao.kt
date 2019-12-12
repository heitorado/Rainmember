package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room

import androidx.room.*
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarm (vararg alarmObjects: Alarm)

    @Delete
    fun destroyAlarm (vararg alarmObjects: Alarm)


    @Query("SELECT * FROM alarm ORDER BY alarmTime ASC")
    fun getAll(): List<Alarm>


    @Query("SELECT * FROM alarm WHERE alarmTime LIKE :alarmTime")
    fun getAlarm(alarmTime : String) : Alarm?
}