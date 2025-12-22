package com.aman.attendit.ui.subject

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EditSubjectDialog(
    subjectName: String,
    initialTarget: Int?,
    isEdit: Boolean,
    onSave: (String, Int?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(subjectName) }
    var target by remember { mutableStateOf(initialTarget?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isEdit) "Edit Subject" else "Add Subject",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Subject Name") },
                    placeholder = { Text("e.g. Mathematics") },
                    leadingIcon = { Icon(Icons.Outlined.Book, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = target,
                    onValueChange = { target = it },
                    label = { Text("Target Attendance %") },
                    placeholder = { Text("Leave empty for overall") },
                    leadingIcon = { Icon(Icons.Outlined.Percent, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val targetValue = target.toIntOrNull()
                    if (name.isBlank()) {
                        Toast.makeText(context, "Subject name cannot be empty", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (target.isNotBlank() && (targetValue == null || targetValue !in 1..100)) {
                        Toast.makeText(context, "Enter target between 1 and 100", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    onSave(name.trim(), targetValue)
                },
                shape = RoundedCornerShape(8.dp)
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}