package com.aman.attendit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem("dashboard", "Dashboard", Icons.Default.Home)
    object Attendance : BottomNavItem("attendance", "Attendance", Icons.Default.CheckCircle)
    object Subjects : BottomNavItem("subjects", "Subjects", Icons.Default.MenuBook)
    object Timetable : BottomNavItem("timetable", "Timetable", Icons.Default.Schedule)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}