package com.aman.attendit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String = "local_user",
    val name: String = "User",
    val email: String = "",
    val overallTargetAttendance: Int = 75
)
