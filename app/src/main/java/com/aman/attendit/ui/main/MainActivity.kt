package com.aman.attendit.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aman.attendit.auth.AuthActivity
import com.aman.attendit.ui.navigation.BottomNavItem
import com.aman.attendit.ui.navigation.NavGraph
import com.aman.attendit.ui.theme.AttendItTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val scaleX = ObjectAnimator.ofFloat(splashScreenView.iconView, View.SCALE_X, 1f, 0f)
            val scaleY = ObjectAnimator.ofFloat(splashScreenView.iconView, View.SCALE_Y, 1f, 0f)

            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            )

            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 500L
            slideUp.doOnEnd { splashScreenView.remove() }

            scaleX.start()
            scaleY.start()
            slideUp.start()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        setContent {
            AttendItTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(
                    BottomNavItem.Dashboard,
                    BottomNavItem.Attendance,
                    BottomNavItem.Subjects,
                    BottomNavItem.Timetable,
                    BottomNavItem.Profile
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val showBottomBar = currentDestination?.route !in listOf("login", "register")

                        AnimatedVisibility(
                            visible = showBottomBar,
                            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .navigationBarsPadding()
                                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(72.dp),
                                    shape = RoundedCornerShape(24.dp),
                                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                                    tonalElevation = 8.dp,
                                    shadowElevation = 10.dp
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        items.forEach { item ->
                                            val isSelected = currentDestination?.hierarchy?.any {
                                                it.route == item.route
                                            } == true

                                            IconButton(
                                                onClick = {
                                                    navController.navigate(item.route) {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Icon(
                                                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                                        contentDescription = item.title,
                                                        modifier = Modifier.size(24.dp),
                                                        tint = if (isSelected)
                                                            MaterialTheme.colorScheme.primary
                                                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                                    )
                                                    if (isSelected) {
                                                        Text(
                                                            text = item.title,
                                                            style = MaterialTheme.typography.labelSmall,
                                                            color = MaterialTheme.colorScheme.primary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) { padding ->
                    NavGraph(
                        navController = navController,
                        startDestination = BottomNavItem.Dashboard.route,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}