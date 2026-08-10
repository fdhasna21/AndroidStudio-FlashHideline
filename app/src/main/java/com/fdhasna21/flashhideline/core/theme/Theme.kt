package com.fdhasna21.flashhideline.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AccentElectricAmber,
    onPrimary = PrimaryDeepSlate,
    background = DarkBackground,       // #1E293B (Deep Slate)
    surface = DarkSurface,
    onBackground = DarkOnBackground,   // #FAFAFA
    onSurface = DarkOnBackground
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDeepSlate,
    onPrimary = LightBackground,
    secondary = AccentElectricAmber,
    background = LightBackground,      // #FFFFFF (Putih)
    surface = LightSurface,            // #FAFAFA
    onBackground = LightOnBackground,  // #18181B
    onSurface = LightOnBackground
)

@Composable
fun FlashHidelineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}