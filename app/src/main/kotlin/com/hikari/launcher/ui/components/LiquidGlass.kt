package com.hikari.launcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hikari.launcher.ui.theme.CosmicBlack
import com.hikari.launcher.ui.theme.GlassBorder
import com.hikari.launcher.ui.theme.GlassInnerShadow
import com.hikari.launcher.ui.theme.GlassTint

/**
 * Contenedor con efecto "Liquid Glass" al estilo Smart Launcher / VoidLauncher.
 *
 * - Fondo translúcido con gradiente sutil (simula refracción del vidrio).
 * - Borde oscuro fino (dark border).
 * - Sombra interna en el borde superior/izquierdo (inner shadow) para dar profundidad.
 * - Sombra exterior suave para elevación.
 *
 * @param cornerRadius radio de redondeo del vidrio.
 * @param tintAlpha nivel de translucidez del vidrio (0f..1f).
 * @param elevation sombra exterior.
 */
@Composable
fun LiquidGlass(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    tintAlpha: Float = 0.08f,
    elevation: Dp = 8.dp,
    shape: Shape = RoundedCornerShape(cornerRadius),
    content: @Composable BoxScope.() -> Unit
) {
    val borderColor = GlassBorder
    val innerShadowColor = GlassInnerShadow

    Box(
        modifier = modifier
            .clip(shape)
            // Fondo translúcido con gradiente vertical sutil (refracción del vidrio)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassTint.copy(alpha = tintAlpha + 0.04f),
                        GlassTint.copy(alpha = tintAlpha),
                        GlassTint.copy(alpha = tintAlpha * 0.6f)
                    )
                )
            )
            // Borde oscuro característico del liquid glass
            .border(
                width = 1.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        borderColor.copy(alpha = 0.9f),
                        borderColor.copy(alpha = 0.4f),
                        borderColor.copy(alpha = 0.8f)
                    )
                ),
                shape = shape
            )
            // Sombra exterior suave
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = CosmicBlack.copy(alpha = 0.6f),
                spotColor = CosmicBlack.copy(alpha = 0.5f)
            )
            // Sombra interna simulada con un dibujo detrás del contenido
            .drawBehind {
                val w = size.width
                val h = size.height
                // Inner shadow en el borde superior
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            innerShadowColor.copy(alpha = 0.45f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = h * 0.18f
                    )
                )
                // Inner shadow en el borde izquierdo
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            innerShadowColor.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        startX = 0f,
                        endX = w * 0.18f
                    )
                )
                // Brillo sutil en el borde inferior derecho (refracción de luz)
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            GlassTint.copy(alpha = 0.06f)
                        ),
                        start = Offset(w * 0.5f, h * 0.5f),
                        end = Offset(w, h),
                        tileMode = TileMode.Clamp
                    )
                )
            },
        content = content
    )
}
