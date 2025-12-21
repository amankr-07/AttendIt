package com.aman.attendit.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus
import com.aman.attendit.data.repository.AttendanceRepository
import com.aman.attendit.data.repository.SubjectRepository
import com.aman.attendit.data.repository.TimetableRepository
import com.aman.attendit.ui.timetable.TimetableUiModel
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
        subjectId: Int,
        status: AttendanceStatus
    ) {
        viewModelScope.launch {
            attendanceRepository.markAttendanceOnce(
                subjectId = subjectId,
                date = todayStartMillis(),
                status = status
            )
        }
    }

    fun updateAttendance(record: AttendanceEntity) {
        viewModelScope.launch {
            attendanceRepository.updateAttendance(record)
        }
    }

    fun deleteAttendance(attendanceId: Int) {
        viewModelScope.launch {
            attendanceRepository.deleteAttendance(attendanceId)
        }
    }

    fun todayClasses(): Flow<List<TimetableUiModel>> {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return timetableRepository
            .getTimetableForDay(today)
            .combine(subjectRepository.getAllSubjects()) { timetable, subjects ->
                timetable.map { entry ->
                    val subjectName = subjects
                        .find { it.subjectId == entry.subjectId }
                        ?.subjectName ?: "Unknown"

                    TimetableUiModel(entry, subjectName)
                }
            }
    }

}

