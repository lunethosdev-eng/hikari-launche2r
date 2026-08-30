package com.hikari.launcher.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hikari.launcher.ui.theme.HikariCyan
import com.hikari.launcher.ui.theme.TextPrimary
import com.hikari.launcher.ui.theme.TextSecondary

/**
 * Barra de bsqueda estilo "Hold here" al estilo VoidLauncher.
 *
 * - Estado colapsado: una pldora liquid glass con el texto "Mantn aqu para buscar" y un cono de lupa.
 * - Estado expandido: un campo de texto real dentro del vidrio donde el usuario escribe.
 */
@Composable
fun GlassSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // Pulso sutil del hint cuando est colapsado (invita a interactuar)
    val transition = rememberInfiniteTransition(label = "search_pulse")
    val hintAlpha by transition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.9f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = tween(1800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "hintPulse"
    )

    LiquidGlass(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        cornerRadius = 26.dp,
        tintAlpha = 0.05f,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (!expanded) {
                // Estado colapsado: "Mantn aqu para buscar"
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(26.dp))
                        .clickable { expanded = true }
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = HikariIcons.Search,
                        contentDescription = "Buscar",
                        tint = HikariCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Manten aqui para buscar",
                        color = TextSecondary.copy(alpha = hintAlpha),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                // Estado expandido: campo de texto real
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = HikariIcons.Search,
                        contentDescription = "Buscar",
                        tint = HikariCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Buscar apps...",
                                color = TextSecondary,
                                fontSize = 15.sp
                            )
                        }
                        BasicTextField(
                            value = query,
                            onValueChange = onQueryChanged,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = TextPrimary,
                                fontSize = 15.sp
                            ),
                            cursorBrush = SolidColor(HikariCyan),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { /* podra lanzar bsqueda */ }),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (query.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = HikariIcons.Search,
                            contentDescription = "Limpiar",
                            tint = TextSecondary,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { onQueryChanged("") }
                        )
                    }
                }
            }
        }
    }
}
