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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

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
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (todayClasses.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No classes today 🎉",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enjoy your free time or check your timetable",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(todayClasses) { item ->
                        val currentStatus = item.entity.status

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
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Today's Schedule",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    val currentStatus = if (item.isMarked) item.entity.status else null

                                    FilledTonalIconButton(
                                        onClick = { viewModel.markAttendance(item.entity.subjectId, AttendanceStatus.PRESENT) },
                                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                                            containerColor = if (currentStatus == AttendanceStatus.PRESENT)
                                                MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = if (currentStatus == AttendanceStatus.PRESENT)
                                                MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    ) { Text("✔") }

                                    FilledTonalIconButton(
                                        onClick = { viewModel.markAttendance(item.entity.subjectId, AttendanceStatus.ABSENT) },
                                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                                            containerColor = if (currentStatus == AttendanceStatus.ABSENT)
                                                MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = if (currentStatus == AttendanceStatus.ABSENT)
                                                MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    ) { Text("✘") }

                                    FilledTonalIconButton(
                                        onClick = { viewModel.markAttendance(item.entity.subjectId, AttendanceStatus.CANCELLED) },
                                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                                            containerColor = if (currentStatus == AttendanceStatus.CANCELLED)
                                                MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = if (currentStatus == AttendanceStatus.CANCELLED)
                                                MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurfaceVariant
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