package com.aman.attendit.data.repository

import com.aman.attendit.data.local.dao.SubjectDao
import com.aman.attendit.data.local.entity.SubjectEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepository @Inject constructor(
    private val subjectDao: SubjectDao
) {

    fun getAllSubjects() = subjectDao.getAllSubjects()

    suspend fun saveSubject(subject: SubjectEntity) {
        subjectDao.insertSubject(subject)
    }

    suspend fun updateSubject(subject: SubjectEntity) {
        subjectDao.insertSubject(subject)
    }

    suspend fun deleteSubject(subjectId: Int) {
        subjectDao.deleteSubjectById(subjectId)
    }
}
