package com.aman.attendit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aman.attendit.data.local.dao.AttendanceDao
import com.aman.attendit.data.local.dao.SubjectDao
import com.aman.attendit.data.local.dao.TimetableDao
import com.aman.attendit.data.local.dao.UserDao
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.SubjectEntity
import com.aman.attendit.data.local.entity.TimetableEntity
import com.aman.attendit.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        SubjectEntity::class,
        TimetableEntity::class,
        AttendanceEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun subjectDao(): SubjectDao

    abstract fun timetableDao(): TimetableDao

    abstract fun attendanceDao(): AttendanceDao
}