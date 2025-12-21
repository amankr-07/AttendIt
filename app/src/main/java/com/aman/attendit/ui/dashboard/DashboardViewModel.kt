package com.aman.attendit.ui.dashboard

import androidx.lifecycle.ViewModel
import com.aman.attendit.data.repository.AttendanceRepository
import com.aman.attendit.data.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    val subjects = subjectRepository.getAllSubjects()

    fun attendanceFlow(subjectId: Int) = attendanceRepository.getAttendanceBySubject(subjectId)
}