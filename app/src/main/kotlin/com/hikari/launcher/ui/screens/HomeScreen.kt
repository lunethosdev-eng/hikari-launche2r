package com.hikari.launcher.ui.screens

import android.content.Intent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hikari.launcher.ui.components.ClockWidget
import com.hikari.launcher.ui.components.CosmicBackground
import com.hikari.launcher.ui.components.GlassAppIcon
import com.hikari.launcher.ui.components.GlassDock
import com.hikari.launcher.ui.components.GlassSearchBar
import com.hikari.launcher.ui.components.HikariIcons
import com.hikari.launcher.ui.components.LiquidGlass
import com.hikari.launcher.ui.components.WeatherWidget
import com.hikari.launcher.ui.theme.HikariCyan
import com.hikari.launcher.ui.viewmodel.AppListViewModel

/**
 * Pantalla principal redisenada al estilo Smart Launcher / VoidLauncher.
 *
 * Capas (de fondo a frente):
 *  1. CosmicBackground: fondo estrellado animado + nebulosa + cometa.
 *  2. Widgets superiores: reloj grande (liquid glass) + widget de clima (liquid glass).
 *  3. Barra de busqueda "Manten aqui para buscar" (liquid glass pill).
 *  4. Grid de apps instaladas con iconos glass + animacion de aparicion escalonada.
 *  5. Dock inferior liquid glass con apps favoritas.
 *  6. Boton de ajustes (icono SVG) en la esquina superior derecha.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit,
    viewModel: AppListViewModel = viewModel()
) {
    val context = LocalContext.current
    val apps by viewModel.filteredApps.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadApps(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // --- Capa 1: fondo cosmico animado ---
        CosmicBackground(modifier = Modifier.fillMaxSize())

        // --- Contenido principal ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Barra superior: boton de ajustes (SVG) en la esquina superior derecha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingsButton(onClick = onSettingsClick)
            }

            // --- Capa 2: widgets (reloj + clima) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ClockWidget(modifier = Modifier.weight(1f))
                WeatherWidget(modifier = Modifier.weight(0.62f))
            }

            // --- Capa 3: barra de busqueda ---
            GlassSearchBar(
                query = searchQuery,
                onQueryChanged = { viewModel.searchApps(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // --- Capa 4: grid de apps instaladas ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                state = gridState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(apps, key = { it.packageName }) { app ->
                    GlassAppIcon(
                        icon = app.icon,
                        label = app.getDisplayLabel(),
                        index = apps.indexOf(app),
                        onClick = { launchApp(context, app.packageName) },
                        onLongPress = { /* futuro: menu contextual */ }
                    )
                }
            }
        }

        // --- Capa 5: dock inferior liquid glass ---
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            // Favoritos: por ahora las primeras apps cargadas como favoritos
            GlassDock(
                apps = apps.take(5),
                onAppClick = { packageName -> launchApp(context, packageName) }
            )
        }
    }
}

/**
 * Boton de ajustes con icono SVG propio y animacion de escala al pulsar.
 * Es una pastilla liquid glass circular.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SettingsButton(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "settings_scale"
    )

    LiquidGlass(
        modifier = Modifier
            .size(44.dp)
            .scale(scale),
        cornerRadius = 22.dp,
        tintAlpha = 0.06f,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = {
                        pressed = true
                        onClick()
                    },
                    onLongClick = { /* sin accion extra */ }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = HikariIcons.Settings,
                contentDescription = "Ajustes",
                tint = HikariCyan,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

/**
 * Lanza una app por nombre de paquete.
 */
fun launchApp(context: android.content.Context, packageName: String) {
    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
