package com.aman.attendit.data.local.dao

import androidx.room.*
import com.aman.attendit.data.local.entity.TimetableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {

    @Query("SELECT * FROM timetable WHERE dayOfWeek = :day")
    fun getTimetableForDay(day: Int): Flow<List<TimetableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: TimetableEntity)

    @Update
    suspend fun updateEntry(entry: TimetableEntity)

    @Query("DELETE FROM timetable WHERE timetableId = :id")
    suspend fun deleteEntry(id: Int)

    @Query("DELETE FROM timetable")
    suspend fun deleteAllTimetable()
}
