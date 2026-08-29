package com.hikari.launcher.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer

object AnimationUtils {
    
    fun getSpringSpec(
        dampingRatio: Float = Spring.DampingRatioMediumBouncy,
        stiffness: Float = Spring.StiffnessMedium
    ) = spring(
        dampingRatio = dampingRatio,
        stiffness = stiffness
    )

    fun getBounceSpec() = spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessMedium
    )

    fun getSmoothSpec() = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow
    )
}

@Composable
fun ScaleAnimationModifier(
    scale: Animatable<Float, AnimationVector1D>,
    modifier: Modifier = Modifier
): Modifier = modifier.scale(scale.value)

@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier
): Modifier {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "pulse")
    val scale = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    return modifier.scale(scale.value)
}

@Composable
fun ShakeAnimation(
    modifier: Modifier = Modifier
): Modifier {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "shake")
    val offsetX = infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessHigh
            ),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "shake_x"
    )

    return modifier.graphicsLayer(translationX = offsetX.value)
}

@Composable
fun RotateAnimation(
    modifier: Modifier = Modifier,
    speed: Float = 1f
): Modifier {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "rotate")
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(
                durationMillis = (2000 / speed).toInt(),
                easing = androidx.compose.animation.core.LinearEasing
            ),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
        ),
        label = "rotate"
    )

    return modifier.graphicsLayer(rotationZ = rotation.value)
}
