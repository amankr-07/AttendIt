package com.aman.attendit.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aman.attendit.data.local.entity.AttendanceEntity
import com.aman.attendit.data.local.entity.AttendanceStatus
import com.aman.attendit.data.local.entity.SubjectEntity
import com.aman.attendit.utils.AttendanceCalculator
import com.aman.attendit.utils.AttendanceState

@Composable
fun DashboardAnalyticsCard(
    subject: SubjectEntity,
    attendance: List<AttendanceEntity>
) {
    val validClasses = attendance.filter {
        it.status != AttendanceStatus.CANCELLED
    }

    val totalClasses = validClasses.size
    val presentClasses = validClasses.count {
        it.status == AttendanceStatus.PRESENT
    }

    val percentage = AttendanceCalculator.calculatePercentage(attendance)
    val target = subject.targetAttendancePercentage ?: 75
    val state = AttendanceCalculator.calculateStatus(
        attendance = attendance,
        target = target
    )

    val (statusText, statusColor) = when (state) {
        AttendanceState.NOT_STARTED ->
            "Not Started" to MaterialTheme.colorScheme.outline

        AttendanceState.SAFE ->
            "Safe" to MaterialTheme.colorScheme.primary

        AttendanceState.BORDERLINE ->
            "Borderline" to MaterialTheme.colorScheme.tertiary

        AttendanceState.DANGER ->
            "Danger" to MaterialTheme.colorScheme.error
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = subject.subjectName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Goal: $target%",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    contentColor = statusColor,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = statusText.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (state == AttendanceState.NOT_STARTED) {
                Text(
                    text = "No classes conducted yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "Attendance",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LinearProgressIndicator(
                    progress = { (percentage.toFloat() / 100f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .padding(vertical = 4.dp),
                    color = statusColor,
                    trackColor = statusColor.copy(alpha = 0.1f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
                )

                Text(
                    text = "$presentClasses attended of $totalClasses total",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}