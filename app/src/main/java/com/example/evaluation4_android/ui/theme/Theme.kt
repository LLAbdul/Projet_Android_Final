package com.example.evaluation4_android.ui.theme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = VioletDeep,
    onPrimary = TextOnDark,
    secondary = PurpleAccent,
    onSecondary = TextOnDark,
    background = CharcoalBlack,
    onBackground = TextOnDark,
    surface = GreySurface,
    onSurface = TextOnDark,
    tertiary = SecondaryPink
)

private val LightColorScheme = lightColorScheme(
    primary = PurpleAccent,
    onPrimary = Color.White,
    secondary = SecondaryPink,
    onSecondary = Color.White,
    background = LightViolet,
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFF8F8F8),
    onSurface = Color(0xFF1C1B1F),
    tertiary = VioletDeep
)

@Composable
fun Evaluation4_AndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
