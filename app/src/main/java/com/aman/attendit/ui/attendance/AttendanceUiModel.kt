package com.aman.attendit.ui.attendance

import com.aman.attendit.data.local.entity.AttendanceEntity

data class AttendanceUiModel(
    val entity: AttendanceEntity,
    val subjectName: String
)
