package com.aman.attendit.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.auth.AuthRepository
import com.aman.attendit.data.repository.AttendanceRepository
import com.aman.attendit.data.repository.SubjectRepository
import com.aman.attendit.data.repository.TimetableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val attendanceRepository: AttendanceRepository,
    private val subjectRepository: SubjectRepository,
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    fun clearDataAndLogout(onComplete: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val d1 = async { attendanceRepository.deleteAllAttendance() }
                val d2 = async { subjectRepository.deleteAllSubjects() }
                val d3 = async { timetableRepository.deleteAllTimetable() }

                d1.await()
                d2.await()
                d3.await()
            }

            authRepository.logout()

            onComplete()
        }
    }
}