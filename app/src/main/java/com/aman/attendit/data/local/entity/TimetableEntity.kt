package com.aman.attendit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetable")
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true) val timetableId: Int = 0,
    val subjectId: Int,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String
)
