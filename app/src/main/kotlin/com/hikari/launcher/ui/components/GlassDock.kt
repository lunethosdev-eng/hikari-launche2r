package com.hikari.launcher.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hikari.launcher.data.models.AppItem
import com.hikari.launcher.ui.theme.HikariCyan
import kotlinx.coroutines.launch

/**
 * Dock inferior estilo VoidLauncher: una barra liquid glass con las apps favoritas.
 * Las apps favoritas se muestran sin etiqueta, solo el cono, dentro del vidrio.
 */
@Composable
fun GlassDock(
    apps: List<AppItem>,
    onAppClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialOffsetY = { it }
        ) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        LiquidGlass(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            cornerRadius = 32.dp,
            tintAlpha = 0.06f,
            elevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mostrar hasta 5 apps favoritas; rellenar con slots vacos si hay menos
                val displayCount = 5
                val visibleApps = apps.take(displayCount)

                visibleApps.forEach { app ->
                    DockAppItem(
                        app = app,
                        onClick = { onAppClick(app.packageName) }
                    )
                }
                // Slots vacos para mantener el layout simtrico
                repeat(displayCount - visibleApps.size) {
                    Box(modifier = Modifier.size(48.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DockAppItem(
    app: AppItem,
    onClick: () -> Unit
) {
    val pressScale = remember { androidx.compose.animation.core.Animatable(1f) }
    val scope = rememberCoroutineScope()

    // Glow sutil pulsante alrededor del cono favorito
    val transition = rememberInfiniteTransition(label = "dock_glow")
    val glowAlpha by transition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = Modifier
            .size(48.dp)
            .scale(pressScale.value)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            HikariCyan.copy(alpha = glowAlpha),
                            androidx.compose.ui.graphics.Color.Transparent
                        )
                    )
                )
            }
            .clip(RoundedCornerShape(14.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.08f),
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.02f)
                    )
                )
            )
            .combinedClickable(
                onClick = {
                    scope.launch {
                        pressScale.animateTo(
                            0.8f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh)
                        )
                        pressScale.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    }
                    onClick()
                }
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (app.icon != null) {
            AsyncImage(
                model = app.icon,
                contentDescription = app.label,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(HikariCyan.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = app.label.take(1).uppercase(),
                    color = HikariCyan,
                    fontSize = 18.sp
                )
            }
        }
    }
}
