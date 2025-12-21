package com.aman.attendit.data.repository

import com.aman.attendit.data.local.dao.AttendanceDao
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val attendanceDao: AttendanceDao
) {

    fun getAllAttendance() = attendanceDao.getAllAttendance()

    suspend fun markAttendanceOnce(
        subjectId: Int,
        date: Long,
        status: AttendanceStatus
    ) {
        val existing =
            attendanceDao.getAttendanceForSubjectOnDate(subjectId, date)

        if (existing == null) {
            attendanceDao.insertAttendance(
                AttendanceEntity(
                    subjectId = subjectId,
                    date = date,
                    status = status
                )
            )
        } else {
            attendanceDao.updateAttendance(
                existing.copy(status = status)
            )
        }
    }

    suspend fun updateAttendance(entity: AttendanceEntity) {
        attendanceDao.updateAttendance(entity)
    }

    suspend fun deleteAttendance(id: Int) {
        attendanceDao.deleteAttendance(id)
    }

    fun getAttendanceBySubject(subjectId: Int) =
        attendanceDao.getAttendanceBySubject(subjectId)

}
