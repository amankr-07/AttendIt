package com.aman.attendit.ui.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.data.local.entity.TimetableEntity
import com.aman.attendit.data.repository.SubjectRepository
import com.aman.attendit.data.repository.TimetableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    fun timetableWithSubjects(day: Int) =
        combine(
            timetableRepository.getTimetableForDay(day),
            subjectRepository.getAllSubjects()
        ) { timetable, subjects ->
            timetable.map { entry ->
                val subjectName = subjects
                    .find { it.subjectId == entry.subjectId }
                    ?.subjectName ?: "Unknown"

                TimetableUiModel(entry, subjectName)
            }
        }

    fun addTimetableEntry(
        subjectId: Int,
        dayOfWeek: Int,
        startTime: String,
        endTime: String
    ) {
        viewModelScope.launch {
            timetableRepository.insertEntry(
                TimetableEntity(
                    subjectId = subjectId,
                    dayOfWeek = dayOfWeek,
                    startTime = startTime,
                    endTime = endTime
                )
            )
        }
    }

    fun updateTimetable(entry: TimetableEntity) {
        viewModelScope.launch {
            timetableRepository.updateEntry(entry)
        }
    }

    fun deleteTimetable(timetableId: Int) {
        viewModelScope.launch {
            timetableRepository.deleteEntry(timetableId)
        }
    }
}
