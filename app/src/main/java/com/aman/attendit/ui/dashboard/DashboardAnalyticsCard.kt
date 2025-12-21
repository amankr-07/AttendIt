package com.aman.attendit.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = subject.subjectName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            if (state == AttendanceState.NOT_STARTED) {
                Text(
                    text = "No classes conducted yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text("Attendance: $percentage%")
                Text("Classes: $presentClasses / $totalClasses")
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = statusText,
                color = statusColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
