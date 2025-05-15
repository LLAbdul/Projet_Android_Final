package com.example.evaluation4_android.ui.composantes

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.evaluation4_android.R
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun CountdownText(expectedDueDate: Long?) {
    var nowMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            nowMillis = System.currentTimeMillis()
            delay(1000L)
        }
    }

    val millisLeft = expectedDueDate?.minus(nowMillis)
    val isOverdue = millisLeft != null && millisLeft < 0
    val absMillis = abs(millisLeft ?: 0L)
    val days = absMillis / (1000 * 60 * 60 * 24)
    val hours = (absMillis / (1000 * 60 * 60)) % 24
    val minutes = (absMillis / (1000 * 60)) % 60
    val seconds = (absMillis / 1000) % 60

    val countdownText = if (!isOverdue) {
        stringResource(R.string.label_countdown_remaining, days, hours, minutes, seconds)
    } else {
        stringResource(R.string.label_countdown_overdue, days, hours, minutes, seconds)
    }

    val countdownColor = when {
        isOverdue || days == 0L -> Color.Red
        days in 1..3 -> Color(0xFFFFA500)
        else -> Color(0xFF006400)
    }

    Text(
        text = countdownText,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Medium,
        color = countdownColor
    )
}
