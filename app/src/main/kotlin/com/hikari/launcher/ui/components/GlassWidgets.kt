package com.hikari.launcher.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hikari.launcher.ui.theme.HikariCyan
import com.hikari.launcher.ui.theme.HikariIce
import com.hikari.launcher.ui.theme.TextPrimary
import com.hikari.launcher.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Widget de reloj grande en una tarjeta liquid glass.
 * Muestra hora (HH:mm), segundos pequenos, fecha y saludo segun la hora del dia.
 */
@Composable
fun ClockWidget(
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    // Actualiza cada segundo para mover los segundos en vivo
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000L)
        }
    }

    val calendar = remember(currentTime) { Calendar.getInstance().apply { timeInMillis = currentTime } }
    val hourFormat = remember { SimpleDateFormat("HH", Locale.getDefault()) }
    val minuteFormat = remember { SimpleDateFormat("mm", Locale.getDefault()) }
    val secondFormat = remember { SimpleDateFormat("ss", Locale.getDefault()) }
    val dateFormat = remember { SimpleDateFormat("EEEE, d 'de' MMMM", Locale("es", "ES")) }

    val hour = hourFormat.format(currentTime)
    val minute = minuteFormat.format(currentTime)
    val second = secondFormat.format(currentTime)
    val dateStr = dateFormat.format(currentTime)
    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
    val greeting = when (hourOfDay) {
        in 5..11 -> "Buenos dias"
        in 12..18 -> "Buenas tardes"
        else -> "Buenas noches"
    }

    LiquidGlass(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        cornerRadius = 28.dp,
        tintAlpha = 0.07f
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 18.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = greeting,
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$hour:$minute",
                        color = TextPrimary,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Light,
                        letterSpacing = (-1).sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = second,
                        color = HikariCyan,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = dateStr.replaceFirstChar { it.uppercase() },
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }
    }
}

/**
 * Widget de clima en una tarjeta liquid glass.
 * Como no hay API real de clima, muestra datos simulados/estaticos con un icono animado.
 * La estructura esta lista para conectar a una API real mas adelante.
 */
@Composable
fun WeatherWidget(
    modifier: Modifier = Modifier,
    temperature: Int = 21,
    condition: String = "Despejado",
    location: String = "Mi ubicacion",
    high: Int = 24,
    low: Int = 14
) {
    val transition = rememberInfiniteTransition(label = "weather")
    val glowPulse by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowPulse"
    )

    LiquidGlass(
        modifier = modifier
            .height(150.dp),
        cornerRadius = 28.dp,
        tintAlpha = 0.07f
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Glow cian detras del icono
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd)
                    .drawBehind {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    HikariIce.copy(alpha = glowPulse * 0.4f),
                                    Color.Transparent
                                )
                            )
                        )
                    }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$temperature" + "\u00B0",
                    color = TextPrimary,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = condition,
                    color = HikariCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "H: $high\u00B0  L: $low\u00B0",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = location,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
