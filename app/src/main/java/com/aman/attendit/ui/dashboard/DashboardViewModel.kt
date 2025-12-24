package com.aman.attendit.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.data.local.entity.AttendanceStatus
import com.aman.attendit.data.repository.AttendanceRepository
import com.aman.attendit.data.repository.SubjectRepository
import com.aman.attendit.utils.AttendanceCalculator
import com.aman.attendit.utils.AttendanceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class SubjectAttendanceAnalytics(
    val subjectName: String,
    val totalClasses: Int,
    val attendedClasses: Int,
    val percentage: Int,
    val state: AttendanceState
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    attendanceRepository: AttendanceRepository,
    subjectRepository: SubjectRepository
) : ViewModel() {

    private val attendanceFlow = attendanceRepository.getAllAttendance()
    private val subjectsFlow = subjectRepository.getAllSubjects()

    val subjectAnalytics: StateFlow<List<SubjectAttendanceAnalytics>> =
        combine(attendanceFlow, subjectsFlow) { attendance, subjects ->
            subjects.map { subject ->
                val subjectAttendance = attendance.filter {
                    it.subjectId == subject.subjectId &&
                            it.status != AttendanceStatus.CANCELLED
                }

                val total = subjectAttendance.size
                val present = subjectAttendance.count {
                    it.status == AttendanceStatus.PRESENT
                }

                val percentage = AttendanceCalculator.calculatePercentage(subjectAttendance)

                val state = AttendanceCalculator.calculateStatus(
                    attendance = subjectAttendance,
                    target = subject.targetAttendancePercentage ?: 75
                )

                SubjectAttendanceAnalytics(
                    subjectName = subject.subjectName,
                    totalClasses = total,
                    attendedClasses = present,
                    percentage = percentage,
                    state = state
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val overallAttendance: StateFlow<Pair<Int, Float>> =
        attendanceFlow.map { attendance ->
            val valid = attendance.filter {
                it.status != AttendanceStatus.CANCELLED
            }

            val total = valid.size
            val present = valid.count { it.status == AttendanceStatus.PRESENT }
            val percent = if (total == 0) 0f else present * 100f / total

            present to percent
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            0 to 0f
        )
}
