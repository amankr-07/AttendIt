package com.aman.attendit.ui.attendance

import com.aman.attendit.data.local.entity.AttendanceStatus

data class AttendanceUiModel(
    val timetableId: Int,
    val subjectId: Int,
    val subjectName: String,
    val status: AttendanceStatus?,
    val isMarked: Boolean
)
