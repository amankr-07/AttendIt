package com.aman.attendit.utils

import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus

object AttendanceCalculator {

    fun calculatePercentage(attendance: List<AttendanceEntity>): Int {
        val validClasses = attendance.filter {
            it.status != AttendanceStatus.CANCELLED
        }

        val total = validClasses.size
        val present = validClasses.count {
            it.status == AttendanceStatus.PRESENT
        }

        if (total == 0) return 0

        return (present * 100) / total
    }

    fun calculateStatus(
        attendance: List<AttendanceEntity>,
        target: Int
    ): AttendanceState {

        val validClasses = attendance.filter {
            it.status != AttendanceStatus.CANCELLED
        }

        val total = validClasses.size

        if (total == 0) {
            return AttendanceState.NOT_STARTED
        }

        val percentage = calculatePercentage(attendance)

        return when {
            percentage >= target -> AttendanceState.SAFE
            percentage >= target - 5 -> AttendanceState.BORDERLINE
            else -> AttendanceState.DANGER
        }
    }
}
