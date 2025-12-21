package com.aman.attendit.ui.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aman.attendit.utils.toDayName

@Composable
fun DayColumn(
    day: Int,
    viewModel: TimetableViewModel
) {
    val timetable by viewModel
        .timetableWithSubjects(day)
        .collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .width(170.dp)
            .padding(8.dp)
    ) {
        Text(day.toDayName(), style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        timetable.forEach { item ->
            TimetableCard(
                item = item,
                onEdit = { start, end ->
                    viewModel.updateTimetable(
                        item.entity.copy(
                            startTime = start,
                            endTime = end
                        )
                    )
                },
                onDelete = {
                    viewModel.deleteTimetable(item.entity.timetableId)
                }
            )
        }

        if (timetable.isEmpty()) {
            Text("No classes")
        }
    }
}
