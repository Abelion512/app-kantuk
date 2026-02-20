package com.example.drowsinessdetector.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.drowsinessdetector.ui.screens.*

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Calibration : Screen("calibration")
    object Monitoring : Screen("monitoring")
    object Alert : Screen("alert")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    onDrowsinessDetected: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onStartMonitoring = { navController.navigate(Screen.Calibration.route) }
            )
        }
        composable(Screen.Calibration.route) {
            CalibrationScreen(
                onCalibrationComplete = { navController.navigate(Screen.Monitoring.route) },
                onCancel = { navController.popBackStack() }
            )
        }
        composable(Screen.Monitoring.route) {
            MonitoringScreen(
                onEndMonitoring = { navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = true }
                } },
                onDrowsinessDetected = onDrowsinessDetected
            )
        }
        composable(Screen.Alert.route) {
            AlertScreen(
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
