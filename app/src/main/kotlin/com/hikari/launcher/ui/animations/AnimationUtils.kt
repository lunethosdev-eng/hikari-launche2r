package com.hikari.launcher.ui.animations

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

object AnimationUtils {
    @Composable
    fun rememberScaleAnimation(targetValue: Float): Float {
        val scale by animateFloatAsState(
            targetValue = targetValue,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        return scale
    }
}