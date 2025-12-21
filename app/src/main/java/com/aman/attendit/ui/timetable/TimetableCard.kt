package com.aman.attendit.ui.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aman.attendit.ui.common.ConfirmDeleteDialog

@Composable
fun TimetableCard(
    item: TimetableUiModel,
    onEdit: (String, String) -> Unit,
    onDelete: () -> Unit
) {
    var showEdit by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(Modifier.padding(10.dp)) {

            Text(
                text = item.subjectName,
                style = MaterialTheme.typography.titleSmall
            )

            Text("${item.entity.startTime} - ${item.entity.endTime}")

            Spacer(Modifier.height(6.dp))

            Row {
                TextButton(onClick = { showEdit = true }) {
                    Text("Edit")
                }
                TextButton(onClick = { showDeleteConfirm = true }) {
                    Text("Delete")
                }
            }

            if (showEdit) {
                EditTimetableDialog(
                    entry = item.entity,
                    subjectName = item.subjectName,
                    onSave = { start, end ->
                        onEdit(start, end)
                        showEdit = false
                    },
                    onDismiss = { showEdit = false }
                )
            }

            if (showDeleteConfirm) {
                ConfirmDeleteDialog(
                    message = "Delete ${item.subjectName} class?",
                    onConfirm = {
                        onDelete()
                        showDeleteConfirm = false
                    },
                    onDismiss = {
                        showDeleteConfirm = false
                    }
                )
            }
        }
    }
}
