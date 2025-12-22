package com.aman.attendit.ui.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aman.attendit.data.local.entity.SubjectEntity
import com.aman.attendit.data.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    val subjects: StateFlow<List<SubjectEntity>> =
        subjectRepository.getAllSubjects()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addSubject(
        name: String,
        targetAttendancePercentage: Int?
    ) {
        if (name.isBlank()) return

        viewModelScope.launch {
            subjectRepository.insertSubject(
                SubjectEntity(
                    subjectName = name.trim(),
                    targetAttendancePercentage = targetAttendancePercentage
                )
            )
        }
    }

    fun updateSubject(
        subjectId: Int,
        newName: String,
        newTarget: Int?
    ) {
        viewModelScope.launch {
            subjectRepository.updateSubject(
                SubjectEntity(
                    subjectId = subjectId,
                    subjectName = newName,
                    targetAttendancePercentage = newTarget
                )
            )
        }
    }
}