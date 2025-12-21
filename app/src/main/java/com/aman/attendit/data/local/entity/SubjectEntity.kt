package com.aman.attendit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val subjectId: Int = 0,
    val subjectName: String,
    val targetAttendancePercentage: Int? = null
)
