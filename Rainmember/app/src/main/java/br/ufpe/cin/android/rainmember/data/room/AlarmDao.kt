package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room

import androidx.room.*
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarm (vararg alarmObjects: Alarm)

    @Query("SELECT * FROM alarm")
    fun getAll(): Array<Alarm>
}