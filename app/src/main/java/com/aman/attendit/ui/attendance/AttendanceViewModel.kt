package com.aman.attendit.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus
import com.aman.attendit.data.repository.AttendanceRepository
import com.aman.attendit.data.repository.SubjectRepository
import com.aman.attendit.data.repository.TimetableRepository
import com.aman.attendit.utils.todayStartMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val subjectRepository: SubjectRepository,
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    fun markAttendance(subjectId: Int, status: AttendanceStatus) {
        viewModelScope.launch {
            attendanceRepository.markAttendanceOnce(
                subjectId = subjectId,
                date = todayStartMillis(),
                status = status
            )
        }
    }

    fun todayClasses(): Flow<List<AttendanceUiModel>> {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val todayMillis = todayStartMillis()

        return combine(
            timetableRepository.getTimetableForDay(today),
            subjectRepository.getAllSubjects(),
            attendanceRepository.getAllAttendance()
        ) { timetable, subjects, allAttendance ->
            timetable.map { entry ->
                val subjectName = subjects.find { it.subjectId == entry.subjectId }?.subjectName ?: "Unknown"
                val existingRecord = allAttendance.find { it.subjectId == entry.subjectId && it.date == todayMillis }

                AttendanceUiModel(
                    entity = existingRecord ?: AttendanceEntity(
                        subjectId = entry.subjectId,
                        date = todayMillis,
                        status = AttendanceStatus.PRESENT
                    ),
                    subjectName = subjectName,
                    isMarked = existingRecord != null
                )
            }
        }
    }
}