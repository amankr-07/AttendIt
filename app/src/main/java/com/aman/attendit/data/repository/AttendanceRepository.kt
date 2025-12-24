package com.aman.attendit.data.repository

import com.aman.attendit.data.local.dao.AttendanceDao
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val dao: AttendanceDao
) {

    fun getAllAttendance(): Flow<List<AttendanceEntity>> =
        dao.getAllAttendance()

    fun getAttendanceBySubject(subjectId: Int): Flow<List<AttendanceEntity>> =
        dao.getAttendanceBySubject(subjectId)

    suspend fun markAttendance(
        timetableId: Int,
        subjectId: Int,
        date: Long,
        status: AttendanceStatus
    ) {
        val existing = dao.getAttendanceForSession(timetableId, date)

        dao.insertAttendance(
            AttendanceEntity(
                attendanceId = existing?.attendanceId ?: 0,
                timetableId = timetableId,
                subjectId = subjectId,
                date = date,
                status = status
            )
        )
    }

    suspend fun deleteAllAttendance() {
        dao.deleteAllAttendance()
    }

}
