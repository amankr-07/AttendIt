package com.aman.attendit.data.local.dao

import androidx.room.*
import com.aman.attendit.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Query("SELECT * FROM attendance ORDER BY date DESC")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

    @Query("""
        SELECT * FROM attendance
        WHERE subjectId = :subjectId AND date = :date
        LIMIT 1
    """)
    suspend fun getAttendanceForSubjectOnDate(
        subjectId: Int,
        date: Long
    ): AttendanceEntity?

    @Query("""
        SELECT * FROM attendance
        WHERE subjectId = :subjectId
        ORDER BY date DESC
    """)
    fun getAttendanceBySubject(
        subjectId: Int
    ): Flow<List<AttendanceEntity>>

    @Insert
    suspend fun insertAttendance(entity: AttendanceEntity)

    @Update
    suspend fun updateAttendance(entity: AttendanceEntity)

    @Query("DELETE FROM attendance WHERE attendanceId = :id")
    suspend fun deleteAttendance(id: Int)
}
