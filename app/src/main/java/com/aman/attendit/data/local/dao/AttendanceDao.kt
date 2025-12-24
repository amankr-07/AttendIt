package com.aman.attendit.data.local.dao

import androidx.room.*
import com.aman.attendit.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Query("SELECT * FROM attendance")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    @Query("""
        SELECT * FROM attendance 
        WHERE timetableId = :timetableId AND date = :date
    """)
    suspend fun getAttendanceForSession(
        timetableId: Int,
        date: Long
    ): AttendanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendance WHERE subjectId = :subjectId")
    fun getAttendanceBySubject(subjectId: Int): Flow<List<AttendanceEntity>>

    @Query("DELETE FROM attendance")
    suspend fun deleteAllAttendance()
}
