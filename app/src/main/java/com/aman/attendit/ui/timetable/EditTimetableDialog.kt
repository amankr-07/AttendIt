package com.aman.attendit.ui.timetable

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aman.attendit.data.local.entity.TimetableEntity
import com.aman.attendit.utils.TimeValidator
import androidx.compose.ui.platform.LocalContext

@Composable
fun EditTimetableDialog(
    entry: TimetableEntity,
    subjectName: String,
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var start by remember { mutableStateOf(entry.startTime) }
    var end by remember { mutableStateOf(entry.endTime) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Timetable") },
        text = {
            Column {
                Text("Subject: $subjectName")

                Spacer(Modifier.height(8.dp))

                TextField(
                    value = start,
                    onValueChange = { start = it },
                    label = { Text("Start Time (hh:mm AM/PM)") }
                )

                TextField(
                    value = end,
                    onValueChange = { end = it },
                    label = { Text("End Time (hh:mm AM/PM)") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {

                if (start.isBlank() || end.isBlank()) {
                    Toast.makeText(
                        context,
                        "Start and/or End time cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }

                if (!TimeValidator.isValid(start) || !TimeValidator.isValid(end)) {
                    Toast.makeText(
                        context,
                        "Invalid time format. Use hh:mm AM/PM",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }

                onSave(start.trim(), end.trim())

            }) {
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
