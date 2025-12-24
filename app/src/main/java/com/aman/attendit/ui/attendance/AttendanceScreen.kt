package com.aman.attendit.ui.attendance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aman.attendit.data.local.entity.AttendanceStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    viewModel: AttendanceViewModel = hiltViewModel()
) {
    val todayClasses by viewModel.todayClasses().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Attendance",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (todayClasses.isEmpty()) {
                Text(
                    text = "No classes today 🎉",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(todayClasses) { item ->

                        val currentStatus = item.status

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.subjectName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                                    FilledTonalIconButton(
                                        onClick = {
                                            viewModel.markAttendance(
                                                timetableId = item.timetableId,
                                                subjectId = item.subjectId,
                                                status = AttendanceStatus.PRESENT
                                            )
                                        },
                                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                                            containerColor =
                                                if (currentStatus == AttendanceStatus.PRESENT)
                                                    MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) { Text("✔") }

                                    FilledTonalIconButton(
                                        onClick = {
                                            viewModel.markAttendance(
                                                timetableId = item.timetableId,
                                                subjectId = item.subjectId,
                                                status = AttendanceStatus.ABSENT
                                            )
                                        },
                                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                                            containerColor =
                                                if (currentStatus == AttendanceStatus.ABSENT)
                                                    MaterialTheme.colorScheme.error
                                                else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) { Text("✘") }

                                    FilledTonalIconButton(
                                        onClick = {
                                            viewModel.markAttendance(
                                                timetableId = item.timetableId,
                                                subjectId = item.subjectId,
                                                status = AttendanceStatus.CANCELLED
                                            )
                                        },
                                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                                            containerColor =
                                                if (currentStatus == AttendanceStatus.CANCELLED)
                                                    MaterialTheme.colorScheme.tertiary
                                                else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) { Text("🚫") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}