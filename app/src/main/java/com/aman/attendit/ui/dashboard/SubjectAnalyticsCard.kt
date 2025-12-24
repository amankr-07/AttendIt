package com.aman.attendit.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aman.attendit.utils.AttendanceState

@Composable
fun SubjectAnalyticsCard(
    data: SubjectAttendanceAnalytics
) {
    val statusColor = when (data.state) {
        AttendanceState.SAFE -> MaterialTheme.colorScheme.primary
        AttendanceState.BORDERLINE -> MaterialTheme.colorScheme.tertiary
        AttendanceState.DANGER -> MaterialTheme.colorScheme.error
        AttendanceState.NOT_STARTED -> MaterialTheme.colorScheme.outline
    }

    val statusText = when (data.state) {
        AttendanceState.SAFE -> "Safe"
        AttendanceState.BORDERLINE -> "Borderline"
        AttendanceState.DANGER -> "At Risk"
        AttendanceState.NOT_STARTED -> "Not Started"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.subjectName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = statusColor.copy(alpha = 0.12f),
                        labelColor = statusColor
                    ),
                    border = null
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${data.percentage}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
                Text(
                    text = "%",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = statusColor
                )
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = "${data.attendedClasses} / ${data.totalClasses} classes attended",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = data.percentage / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = statusColor,
                trackColor = statusColor.copy(alpha = 0.15f)
            )
        }
    }
}