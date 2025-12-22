package com.aman.attendit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Dashboard : BottomNavItem(
        "dashboard", "Home",
        Icons.Outlined.SpaceDashboard, Icons.Rounded.SpaceDashboard
    )
    object Attendance : BottomNavItem(
        "attendance", "Today",
        Icons.Outlined.CheckCircle, Icons.Rounded.CheckCircle
    )
    object Subjects : BottomNavItem(
        "subjects", "Subjects",
        Icons.Outlined.LibraryBooks, Icons.Rounded.LibraryBooks
    )
    object Timetable : BottomNavItem(
        "timetable", "Schedule",
        Icons.Outlined.DateRange, Icons.Rounded.DateRange
    )
    object Profile : BottomNavItem(
        "profile", "Me",
        Icons.Outlined.Person, Icons.Rounded.Person
    )
}