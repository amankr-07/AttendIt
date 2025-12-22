package com.aman.attendit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aman.attendit.auth.LoginScreen
import com.aman.attendit.auth.RegisterScreen
import com.aman.attendit.ui.attendance.AttendanceScreen
import com.aman.attendit.ui.dashboard.DashboardScreen
import com.aman.attendit.ui.profile.ProfileScreen
import com.aman.attendit.ui.subject.SubjectScreen
import com.aman.attendit.ui.timetable.TimetableScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                viewModel = hiltViewModel(),
                onNavigateRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate(BottomNavItem.Dashboard.route) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = hiltViewModel(),
                onRegisterSuccess = {
                    navController.navigate(BottomNavItem.Dashboard.route) {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        composable(BottomNavItem.Dashboard.route) { DashboardScreen() }
        composable(BottomNavItem.Attendance.route) { AttendanceScreen() }
        composable(BottomNavItem.Subjects.route) { SubjectScreen() }
        composable(BottomNavItem.Timetable.route) { TimetableScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}