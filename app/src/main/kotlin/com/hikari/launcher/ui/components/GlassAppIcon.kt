package com.hikari.launcher.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hikari.launcher.ui.theme.CosmicSurfaceVariant
import com.hikari.launcher.ui.theme.HikariCyan
import com.hikari.launcher.ui.theme.TextPrimary
import com.hikari.launcher.ui.theme.TextSecondary
import kotlin.math.min
import kotlinx.coroutines.launch

/**
 * ?cono de app con efecto liquid glass y animacin de aparicin escalonada + escala al tap.
 *
 * - Aparece con un spring bouncy escalado desde 0.6 + fade (escalado escalonado por ndice).
 * - Al pulsar se comprime ligeramente (press scale) y vuelve.
 * - El cono real de la app se muestra dentro de un contenedor translcido con borde glass.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlassAppIcon(
    icon: android.graphics.drawable.Drawable?,
    label: String,
    index: Int = 0,
    onLongPress: () -> Unit = {},
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Aparicin escalonada
    val appearScale = remember { Animatable(0.6f) }
    val appearAlpha = remember { Animatable(0f) }
    // Escala al pulsar
    val pressScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Retardo escalonado (mximo 12 items escalonados, luego aparecen juntos)
        val delayMs = min(index, 11) * 35L
        kotlinx.coroutines.delay(delayMs)
        appearAlpha.animateTo(
            1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
        appearScale.animateTo(
            1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }

    val totalScale = appearScale.value * pressScale.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .graphicsLayer {
                scaleX = totalScale
                scaleY = totalScale
                alpha = appearAlpha.value
            }
            .size(78.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CosmicSurfaceVariant.copy(alpha = 0.55f),
                        CosmicSurfaceVariant.copy(alpha = 0.25f)
                    )
                )
            )
            .combinedClickable(
                onClick = {
                    scope.launch {
                        pressScale.animateTo(
                            0.85f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh)
                        )
                        pressScale.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    }
                    onClick()
                },
                onLongClick = onLongPress
            )
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                AsyncImage(
                    model = icon,
                    contentDescription = label,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder con inicial cuando no hay cono
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(HikariCyan.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label.take(1).uppercase(),
                        color = HikariCyan,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = label,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = TextPrimary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
        )
    }
}
