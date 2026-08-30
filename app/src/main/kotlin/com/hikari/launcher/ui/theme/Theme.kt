package com.hikari.launcher.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema oscuro cósmico (el launcher siempre usa dark theme tipo VoidLauncher)
private val HikariDarkColorScheme = darkColorScheme(
    primary = HikariCyan,
    onPrimary = CosmicBlack,
    primaryContainer = HikariIndigo,
    onPrimaryContainer = TextPrimary,
    secondary = HikariPurple,
    onSecondary = CosmicBlack,
    secondaryContainer = CosmicSurfaceVariant,
    onSecondaryContainer = TextPrimary,
    tertiary = HikariPink,
    onTertiary = CosmicBlack,
    background = CosmicBlack,
    onBackground = TextPrimary,
    surface = CosmicSurface,
    onSurface = TextPrimary,
    surfaceVariant = CosmicSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorder,
    outlineVariant = CosmicSurfaceVariant
)

private val HikariLightColorScheme = lightColorScheme(
    primary = HikariBlue,
    secondary = HikariIndigo,
    tertiary = HikariPink,
    background = CosmicBlack,
    onBackground = TextPrimary,
    surface = CosmicSurface,
    onSurface = TextPrimary
)

@Composable
fun HikariLauncherTheme(
    darkTheme: Boolean = true, // El launcher siempre es dark/cósmico
    dynamicColor: Boolean = false, // Desactivado para mantener la estética cósmica consistente
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> HikariDarkColorScheme
        else -> HikariLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = CosmicBlack.toArgb()
            window.navigationBarColor = CosmicBlack.toArgb()
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
