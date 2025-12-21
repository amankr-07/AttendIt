package com.aman.attendit.ui.attendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus

@Composable
fun EditAttendanceDialog(
    record: AttendanceEntity,
    subjectName: String,
    onSave: (AttendanceStatus) -> Unit,
    onDismiss: () -> Unit
) {
    var selected by remember { mutableStateOf(record.status) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Attendance") },
        text = {
            Column {
                Text(subjectName)
                Spacer(Modifier.height(8.dp))
                AttendanceStatus.values().forEach {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == it,
                            onClick = { selected = it }
                        )
                        Text(it.name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(selected) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
