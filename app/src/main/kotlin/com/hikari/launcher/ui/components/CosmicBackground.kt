package com.hikari.launcher.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hikari.launcher.ui.theme.CosmicBlack
import com.hikari.launcher.ui.theme.CosmicDeep
import com.hikari.launcher.ui.theme.HikariIndigo
import com.hikari.launcher.ui.theme.HikariPurple
import com.hikari.launcher.ui.theme.StarBright
import com.hikari.launcher.ui.theme.StarDim
import kotlin.math.cos
import kotlin.math.sin

/**
 * Estrella individual del campo estrellado.
 */
private data class Star(
    val x: Float,          // posicin relativa 0..1
    val y: Float,          // posicin relativa 0..1
    val radius: Float,     // tamao en px
    val baseAlpha: Float,  // brillo base 0..1
    val twinkleSpeed: Float,
    val phase: Float       // desfase de parpadeo
)

/**
 * Fondo csmico animado al estilo VoidLauncher.
 *
 * Capas (de atrs hacia adelante):
 *  1. Gradiente csmico profundo (nebulosa violeta/azul).
 *  2. Campo de estrellas con parpadeo (twinkle).
 *  3. Cometa / meteoro que cruza peridicamente.
 */
@Composable
fun CosmicBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val transition = rememberInfiniteTransition(label = "cosmic")

    // Tiempo global para el parpadeo de las estrellas
    val twinkleTime by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "twinkle"
    )

    // Progreso del cometa (0..1) â€” aparece cada ~9s
    val cometProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "comet"
    )

    // Respiracin sutil de la nebulosa
    val nebulaPulse by transition.animateFloat(
        initialValue = 0.12f,
        targetValue = 0.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nebula"
    )

    // Campo de estrellas fijo (no se recomputa en cada recomposicin gracias a remember)
    val stars = remember {
        val rnd = kotlin.random.Random(42)
        List(140) {
            Star(
                x = rnd.nextFloat(),
                y = rnd.nextFloat(),
                radius = rnd.nextFloat() * 1.6f + 0.4f,
                baseAlpha = rnd.nextFloat() * 0.6f + 0.3f,
                twinkleSpeed = rnd.nextFloat() * 2f + 0.5f,
                phase = rnd.nextFloat() * 6.28f
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // --- Capa 1: gradiente csmico + nebulosa ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Fondo base: gradiente vertical del negro csmico al azul profundo
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(CosmicBlack, CosmicDeep, CosmicBlack),
                    startY = 0f,
                    endY = h
                )
            )

            // Nebulosa violeta (esquina superior izquierda)
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        HikariPurple.copy(alpha = nebulaPulse),
                        HikariPurple.copy(alpha = 0f)
                    ),
                    center = Offset(w * 0.2f, h * 0.15f),
                    radius = w * 0.6f
                )
            )

            // Nebulosa azul/ndigo (esquina inferior derecha)
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        HikariIndigo.copy(alpha = nebulaPulse * 0.9f),
                        HikariIndigo.copy(alpha = 0f)
                    ),
                    center = Offset(w * 0.8f, h * 0.85f),
                    radius = w * 0.7f
                )
            )
        }

        // --- Capa 2: campo de estrellas con parpadeo ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            stars.forEach { star ->
                val twinkle = (sin((twinkleTime * star.twinkleSpeed + star.phase) * 3.14159f) + 1f) * 0.5f
                val alpha = star.baseAlpha * (0.4f + twinkle * 0.6f)
                val color = if (star.radius > 1.2f) StarBright else StarDim
                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = star.radius,
                    center = Offset(star.x * w, star.y * h)
                )
            }
        }

        // --- Capa 3: cometa / meteoro ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // El cometa recorre una diagonal; solo es visible en parte del ciclo
            val cycle = cometProgress
            if (cycle < 0.35f) {
                val t = cycle / 0.35f
                val startX = w * 1.1f
                val startY = h * -0.1f
                val endX = w * -0.2f
                val endY = h * 0.6f
                val headX = startX + (endX - startX) * t
                val headY = startY + (endY - startY) * t

                // Estela del cometa
                val tailLength = w * 0.25f
                val angleRad = kotlin.math.atan2(endY - startY, endX - startX)
                val tailX = headX - cos(angleRad) * tailLength
                val tailY = headY - sin(angleRad) * tailLength

                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            StarBright.copy(alpha = 0f),
                            HikariIndigo.copy(alpha = 0.4f),
                            StarBright.copy(alpha = 0.95f)
                        ),
                        start = Offset(tailX, tailY),
                        end = Offset(headX, headY)
                    ),
                    start = Offset(tailX, tailY),
                    end = Offset(headX, headY),
                    strokeWidth = 3.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )

                // Cabeza del cometa (ncleo brillante)
                drawCircle(
                    color = StarBright.copy(alpha = 0.9f),
                    radius = 2.5.dp.toPx(),
                    center = Offset(headX, headY)
                )
                // Halo del cometa
                drawCircle(
                    color = HikariIceSafe.copy(alpha = 0.3f),
                    radius = 6.dp.toPx(),
                    center = Offset(headX, headY)
                )
            }
        }

        content()
    }
}

// Alias seguro para HikariIce (definido en Color.kt) â€” evita import circular
private val HikariIceSafe = Color(0xFF7FD4FF)
