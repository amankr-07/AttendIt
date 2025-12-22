package com.aman.attendit.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.data.local.entity.AttendanceStatus
import com.aman.attendit.data.repository.AttendanceRepository
import com.aman.attendit.data.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    val subjects = subjectRepository.getAllSubjects()

    val overallAttendance: StateFlow<Pair<Int, Float>> = attendanceRepository.getAllAttendance()
        .combine(subjects) { attendanceList, _ ->
            val validClasses = attendanceList.filter { it.status != AttendanceStatus.CANCELLED }
            val total = validClasses.size
            val present = validClasses.count { it.status == AttendanceStatus.PRESENT }

            val percentage = if (total > 0) (present.toFloat() / total.toFloat()) * 100f else 0f
            present to percentage
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0 to 0f
        )

    fun attendanceFlow(subjectId: Int) = attendanceRepository.getAttendanceBySubject(subjectId)
}