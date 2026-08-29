package com.hikari.launcher.ui.gestures

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

suspend fun PointerInputScope.detectSwipeGestures(
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {},
    onLongPress: () -> Unit = {},
    swipeThreshold: Dp = 100.dp
) {
    val swipeThresholdPx = swipeThreshold.toPx()

    detectDragGestures(
        onDrag = { change, dragAmount ->
            change.consume()
        },
        onDragEnd = {
            // Handle drag end
        }
    )

    detectTapGestures(
        onLongPress = { onLongPress() }
    )
}

data class SwipeDirection {
    companion object {
        const val LEFT = "left"
        const val RIGHT = "right"
        const val UP = "up"
        const val DOWN = "down"
        const val NONE = "none"
    }
}

fun calculateSwipeDirection(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    threshold: Float = 50f
): String {
    val deltaX = endX - startX
    val deltaY = endY - startY

    return when {
        abs(deltaX) > abs(deltaY) && abs(deltaX) > threshold -> {
            if (deltaX > 0) SwipeDirection.RIGHT else SwipeDirection.LEFT
        }
        abs(deltaY) > abs(deltaX) && abs(deltaY) > threshold -> {
            if (deltaY > 0) SwipeDirection.DOWN else SwipeDirection.UP
        }
        else -> SwipeDirection.NONE
    }
}
