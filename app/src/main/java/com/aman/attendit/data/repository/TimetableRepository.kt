package com.aman.attendit.data.repository

import com.aman.attendit.data.local.dao.TimetableDao
import com.aman.attendit.data.local.entity.TimetableEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepository @Inject constructor(
    private val timetableDao: TimetableDao
) {

    fun getTimetableForDay(day: Int) =
        timetableDao.getTimetableForDay(day)

    suspend fun insertEntry(entry: TimetableEntity) {
        timetableDao.insertEntry(entry)
    }

    suspend fun updateEntry(entry: TimetableEntity) {
        timetableDao.updateEntry(entry)
    }

    suspend fun deleteEntry(id: Int) {
        timetableDao.deleteEntry(id)
    }
}