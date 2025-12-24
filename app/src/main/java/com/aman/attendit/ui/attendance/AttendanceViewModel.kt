package com.aman.attendit.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun markAttendance(
        timetableId: Int,
        subjectId: Int,
        status: AttendanceStatus
    ) {
        viewModelScope.launch {
            attendanceRepository.markAttendance(
                timetableId = timetableId,
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
        ) { timetable, subjects, attendance ->

            timetable.map { entry ->
                val subject = subjects.first { it.subjectId == entry.subjectId }

                val record = attendance.find {
                    it.timetableId == entry.timetableId &&
                            it.date == todayMillis
                }

                AttendanceUiModel(
                    timetableId = entry.timetableId,
                    subjectId = subject.subjectId,
                    subjectName = subject.subjectName,
                    status = record?.status,
                    isMarked = record != null
                )
            }
        }
    }
}
