package com.aman.attendit.ui.timetable

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aman.attendit.data.local.entity.SubjectEntity
import com.aman.attendit.utils.TimeValidator
import java.util.Calendar

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
        title = {
            Text("Add Class Schedule", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PremiumDropdown(
                    label = "Subject",
                    currentValue = selectedSubject?.subjectName ?: "Select Subject",
                    items = subjects,
                    itemLabel = { it.subjectName },
                    onSelect = { selectedSubject = it }
                )

                PremiumDayDropdown(
                    selectedDay = selectedDay,
                    onSelect = { selectedDay = it }
                )

                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time") },
                    placeholder = { Text("09:00 AM") },
                    leadingIcon = { Icon(Icons.Outlined.Schedule, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time") },
                    placeholder = { Text("10:00 AM") },
                    leadingIcon = { Icon(Icons.Outlined.Schedule, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (selectedSubject == null) {
                    Toast.makeText(context, "Please select a subject", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (startTime.isBlank() || endTime.isBlank()) {
                    Toast.makeText(context, "Times cannot be empty", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (!TimeValidator.isValid(startTime) || !TimeValidator.isValid(endTime)) {
                    Toast.makeText(context, "Use format hh:mm AM/PM", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                onSave(selectedSubject!!.subjectId, selectedDay, startTime.trim(), endTime.trim())
            }) { Text("Add Class") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun <T> PremiumDropdown(
    label: String,
    currentValue: String,
    items: List<T>,
    itemLabel: (T) -> String,
    onSelect: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedCard(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentValue,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (currentValue.contains("Select"))
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(itemLabel(item)) },
                        onClick = {
                            onSelect(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PremiumDayDropdown(
    selectedDay: Int,
    onSelect: (Int) -> Unit
) {
    val dayNames = mapOf(
        Calendar.MONDAY to "Monday",
        Calendar.TUESDAY to "Tuesday",
        Calendar.WEDNESDAY to "Wednesday",
        Calendar.THURSDAY to "Thursday",
        Calendar.FRIDAY to "Friday",
        Calendar.SATURDAY to "Saturday"
    )

    PremiumDropdown(
        label = "Day of Week",
        currentValue = dayNames[selectedDay] ?: "Select Day",
        items = dayNames.keys.toList(),
        itemLabel = { dayNames[it] ?: "" },
        onSelect = onSelect
    )
}