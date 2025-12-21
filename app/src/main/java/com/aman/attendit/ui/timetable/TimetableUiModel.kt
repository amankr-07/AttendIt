package com.aman.attendit.ui.timetable

import com.aman.attendit.data.local.entity.TimetableEntity

data class TimetableUiModel(
    val entity: TimetableEntity,
    val subjectName: String
)
