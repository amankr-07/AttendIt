package com.aman.attendit.di

import android.content.Context
import androidx.room.Room
import com.aman.attendit.data.local.dao.AttendanceDao
import com.aman.attendit.data.local.dao.SubjectDao
import com.aman.attendit.data.local.dao.TimetableDao
import com.aman.attendit.data.local.dao.UserDao
import com.aman.attendit.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "attendance_db"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()

    @Provides
    fun provideSubjectDao(db: AppDatabase): SubjectDao =
        db.subjectDao()

    @Provides
    fun provideTimetableDao(db: AppDatabase): TimetableDao =
        db.timetableDao()

    @Provides
    fun provideAttendanceDao(db: AppDatabase): AttendanceDao =
        db.attendanceDao()
}
