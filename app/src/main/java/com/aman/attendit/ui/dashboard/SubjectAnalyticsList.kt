package com.aman.attendit.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aman.attendit.data.local.entity.SubjectEntity

@Composable
fun SubjectAnalyticsList(
    subjects: List<SubjectEntity>,
    viewModel: DashboardViewModel
) {
    Column {
        subjects.forEach { subject ->

            val attendance by viewModel
                .attendanceFlow(subject.subjectId)
                .collectAsState(initial = emptyList())

            DashboardAnalyticsCard(
                subject = subject,
                attendance = attendance
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}
