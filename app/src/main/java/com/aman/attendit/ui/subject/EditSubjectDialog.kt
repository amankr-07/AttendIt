package com.aman.attendit.ui.subject

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun EditSubjectDialog(
    subjectName: String,
    initialTarget: Int?,
    onSave: (String, Int?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(subjectName) }
    var target by remember {
        mutableStateOf(initialTarget?.toString() ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Subject") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Subject Name") }
                )

                Spacer(Modifier.height(8.dp))

                TextField(
                    value = target,
                    onValueChange = { target = it },
                    label = { Text("Target Attendance %") },
                    placeholder = {
                        Text("Leave empty to use overall target")
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val targetValue = target.toIntOrNull()

                if (target.isNotBlank() && (targetValue == null || targetValue !in 1..100)) {
                    Toast.makeText(
                        context,
                        "Enter target between 1 and 100",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@TextButton
                }

                onSave(name, targetValue)
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
