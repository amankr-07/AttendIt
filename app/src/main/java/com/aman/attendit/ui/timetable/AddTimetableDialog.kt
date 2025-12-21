package com.aman.attendit.ui.timetable

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.aman.attendit.data.local.entity.SubjectEntity
import com.aman.attendit.utils.TimeValidator
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddTimetableDialog(
    subjects: List<SubjectEntity>,
    onSave: (Int, Int, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var selectedSubject by remember { mutableStateOf<SubjectEntity?>(null) }
    var selectedDay by remember { mutableStateOf(Calendar.MONDAY) }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Class") },
        text = {
            Column {

                SubjectDropdown(
                    subjects = subjects,
                    selected = selectedSubject,
                    onSelect = { selectedSubject = it }
                )

                Spacer(Modifier.height(8.dp))

                DayDropdown(
                    selectedDay = selectedDay,
                    onSelect = { selectedDay = it }
                )

                Spacer(Modifier.height(8.dp))

                TextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time (hh:mm AM/PM)") }
                )

                TextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time (hh:mm AM/PM)") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {

                if (selectedSubject == null) {
                    Toast.makeText(
                        context,
                        "Please select a subject",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }

                if (startTime.isBlank() || endTime.isBlank()) {
                    Toast.makeText(
                        context,
                        "Start and End time cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }

                if (
                    !TimeValidator.isValid(startTime) ||
                    !TimeValidator.isValid(endTime)
                ) {
                    Toast.makeText(
                        context,
                        "Invalid time format. Use hh:mm AM/PM",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }

                onSave(
                    selectedSubject!!.subjectId,
                    selectedDay,
                    startTime.trim(),
                    endTime.trim()
                )

            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
fun SubjectDropdown(
    subjects: List<SubjectEntity>,
    selected: SubjectEntity?,
    onSelect: (SubjectEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selected?.subjectName ?: "Select Subject")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            subjects.forEach {
                DropdownMenuItem(
                    text = { Text(it.subjectName) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
