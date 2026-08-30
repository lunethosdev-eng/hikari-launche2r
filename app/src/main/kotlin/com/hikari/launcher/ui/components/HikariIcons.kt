package com.hikari.launcher.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Iconos vectoriales (SVG-style) propios de Hikari Launcher.
 * Diseñados con trazos finos redondeados para combinar con la estética cósmica / liquid glass.
 */
object HikariIcons {

    /** Icono de cajón de apps (cuatro puntos en grid + marco redondeado). */
    val AppsDrawer: ImageVector by lazy {
        ImageVector.Builder(
            name = "AppsDrawer",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6f, 6f)
                arcTo(1.5f, 1.5f, 0f, false, true, 7.5f, 4.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 6f, 6f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 6f)
                arcTo(1.5f, 1.5f, 0f, false, true, 13.5f, 4.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 12f, 6f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(18f, 6f)
                arcTo(1.5f, 1.5f, 0f, false, true, 19.5f, 4.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 18f, 6f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(6f, 12f)
                arcTo(1.5f, 1.5f, 0f, false, true, 7.5f, 10.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 6f, 12f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 12f)
                arcTo(1.5f, 1.5f, 0f, false, true, 13.5f, 10.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 12f, 12f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(18f, 12f)
                arcTo(1.5f, 1.5f, 0f, false, true, 19.5f, 10.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 18f, 12f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(6f, 18f)
                arcTo(1.5f, 1.5f, 0f, false, true, 7.5f, 16.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 6f, 18f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 18f)
                arcTo(1.5f, 1.5f, 0f, false, true, 13.5f, 16.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 12f, 18f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(18f, 18f)
                arcTo(1.5f, 1.5f, 0f, false, true, 19.5f, 16.5f)
                arcTo(1.5f, 1.5f, 0f, false, true, 18f, 18f)
                close()
            }
        }.build()
    }

    /** Lupa de búsqueda con trazo fino. */
    val Search: ImageVector by lazy {
        ImageVector.Builder(
            name = "Search",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = null,
                strokeLineWidth = 0f
            ) {
                moveTo(10f, 4f)
                arcTo(6f, 6f, 0f, true, false, 10f, 16f)
                arcTo(6f, 6f, 0f, true, false, 10f, 4f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                stroke = null,
                strokeLineWidth = 0f
            ) {
                moveTo(14.5f, 13.5f)
                lineTo(20f, 19f)
                arcTo(1.5f, 1.5f, 0f, false, true, 17.8f, 21.2f)
                lineTo(12.3f, 15.7f)
                close()
            }
        }.build()
    }

    /** Engranaje de ajustes simplificado (trazo). */
    val Settings: ImageVector by lazy {
        ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Círculo central hueco
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(12f, 8.5f)
                arcTo(3.5f, 3.5f, 0f, true, false, 12f, 15.5f)
                arcTo(3.5f, 3.5f, 0f, true, false, 12f, 8.5f)
                close()
            }
            // 8 dientes alrededor
            for (i in 0 until 8) {
                val angle = (i * 45).toDouble()
                val rad = Math.toRadians(angle)
                val cx = (12 + 7.5 * cos(rad)).toFloat()
                val cy = (12 + 7.5 * sin(rad)).toFloat()
                path(fill = SolidColor(Color.Black)) {
                    moveTo(cx, cy)
                    arcTo(1.4f, 1.4f, 0f, false, false, cx + 1.4f, cy)
                    arcTo(1.4f, 1.4f, 0f, false, false, cx, cy + 1.4f)
                    arcTo(1.4f, 1.4f, 0f, false, false, cx - 1.4f, cy)
                    arcTo(1.4f, 1.4f, 0f, false, false, cx, cy - 1.4f)
                    close()
                }
            }
        }.build()
    }

    /** Icono de widget (tarjeta con esquinas). */
    val Widget: ImageVector by lazy {
        ImageVector.Builder(
            name = "Widget",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(4f, 5f)
                arcTo(2f, 2f, 0f, false, true, 6f, 3f)
                lineTo(18f, 3f)
                arcTo(2f, 2f, 0f, false, true, 20f, 5f)
                lineTo(20f, 19f)
                arcTo(2f, 2f, 0f, false, true, 18f, 21f)
                lineTo(6f, 21f)
                arcTo(2f, 2f, 0f, false, true, 4f, 19f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color.White),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8f, 9f)
                lineTo(16f, 9f)
                moveTo(8f, 13f)
                lineTo(13f, 13f)
            }
        }.build()
    }

    /** Reloj sencillo (manecillas). */
    val Clock: ImageVector by lazy {
        ImageVector.Builder(
            name = "Clock",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = null,
                strokeLineWidth = 0f
            ) {
                moveTo(12f, 3f)
                arcTo(9f, 9f, 0f, true, false, 12f, 21f)
                arcTo(9f, 9f, 0f, true, false, 12f, 3f)
                close()
            }
            path(
                fill = SolidColor(Color.White),
                stroke = null,
                strokeLineWidth = 0f
            ) {
                moveTo(12f, 6.5f)
                lineTo(12f, 12f)
                lineTo(16.5f, 14.5f)
                lineTo(15.5f, 16f)
                lineTo(10.5f, 13f)
                lineTo(10.5f, 6.5f)
                close()
            }
        }.build()
    }

    /** Icono de menú / capas (líneas apiladas tipo "layers"). */
    val Layers: ImageVector by lazy {
        ImageVector.Builder(
            name = "Layers",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                // capa superior (rombo)
                moveTo(12f, 3f)
                lineTo(21f, 8f)
                lineTo(12f, 13f)
                lineTo(3f, 8f)
                close()
            }
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(12f, 11f)
                lineTo(21f, 16f)
                lineTo(12f, 21f)
                lineTo(3f, 16f)
                close()
            }
        }.build()
    }
}

// Helpers trigonométricos para el icono de engranaje
private fun cos(rad: Double): Float = kotlin.math.cos(rad).toFloat()
private fun sin(rad: Double): Float = kotlin.math.sin(rad).toFloat()
