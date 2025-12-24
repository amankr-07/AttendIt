package com.aman.attendit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val attendanceId: Int = 0,
    val timetableId: Int,
    val subjectId: Int,
    val date: Long,
    val status: AttendanceStatus
)
