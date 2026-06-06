package com.confeitaria.gestao.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = RosaConfeitaria,
    onPrimary = BrancoNata,
    primaryContainer = RosaClaro,
    secondary = MarromChocolate,
    onSecondary = BrancoNata,
    secondaryContainer = MarromClaro,
    background = BrancoNata,
    surface = BrancoNata,
)

@Composable
fun ConfeitariaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
