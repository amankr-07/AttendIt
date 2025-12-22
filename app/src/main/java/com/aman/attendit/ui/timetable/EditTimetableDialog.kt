package com.aman.attendit.ui.timetable

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aman.attendit.data.local.entity.TimetableEntity
import com.aman.attendit.utils.TimeValidator

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
        title = {
            Text("Edit Class Timing", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = subjectName,
                    onValueChange = {},
                    label = { Text("Subject") },
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Outlined.Book, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                )

                OutlinedTextField(
                    value = start,
                    onValueChange = { start = it },
                    label = { Text("Start Time") },
                    placeholder = { Text("hh:mm AM/PM") },
                    leadingIcon = { Icon(Icons.Outlined.Schedule, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = end,
                    onValueChange = { end = it },
                    label = { Text("End Time") },
                    placeholder = { Text("hh:mm AM/PM") },
                    leadingIcon = { Icon(Icons.Outlined.Schedule, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (start.isBlank() || end.isBlank()) {
                        Toast.makeText(context, "Times cannot be empty", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (!TimeValidator.isValid(start) || !TimeValidator.isValid(end)) {
                        Toast.makeText(context, "Invalid format. Use hh:mm AM/PM", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    onSave(start.trim(), end.trim())
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}