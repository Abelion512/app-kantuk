package com.example.drowsinessdetector.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784), // Green from design
    secondary = Color(0xFF334B4F),
    tertiary = Color(0xFFD0BCFF),
    background = Color(0xFF000000),
    surface = Color(0xFF121212),
    onPrimary = Color(0xFF003E1C),
    onSurface = Color(0xFFE2E2E2),
    onBackground = Color(0xFFFFFFFF),
    error = Color(0xFFFFB4AB)
)

@Composable
fun DrowsinessDetectorTheme(
    darkTheme: Boolean = true, // Force dark theme for iOS-like dark mode in design
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
