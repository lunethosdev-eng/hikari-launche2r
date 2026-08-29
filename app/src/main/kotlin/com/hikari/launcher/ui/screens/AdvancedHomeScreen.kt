package com.hikari.launcher.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hikari.launcher.data.models.AppItem
import com.hikari.launcher.ui.components.AnimatedAppIcon
import com.hikari.launcher.ui.viewmodel.AppListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AdvancedHomeScreen(
    viewModel: AppListViewModel = viewModel()
) {
    val context = LocalContext.current
    val apps by androidx.lifecycle.compose.collectAsStateWithLifecycle(viewModel.filteredApps)
    val isLoading by androidx.lifecycle.compose.collectAsStateWithLifecycle(viewModel.isLoading)
    val searchQuery by androidx.lifecycle.compose.collectAsStateWithLifecycle(viewModel.searchQuery)

    var selectedApp by remember { mutableStateOf<AppItem?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadApps(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.SpaceBetween
            ) {
                Text(
                    "Hikari Launcher",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChanged = { viewModel.searchApps(it) },
                modifier = Modifier.fillMaxWidth()
            )

            // Apps Grid
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cargando aplicaciones...")
                }
            } else if (apps.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron aplicaciones")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 90.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                ) {
                    items(apps, key = { it.packageName }) { app ->
                        AnimatedAppIcon(
                            icon = app.icon,
                            label = app.label,
                            packageName = app.packageName,
                            onClick = {
                                launchApp(context, app.packageName)
                            },
                            onLongPress = {
                                selectedApp = app
                            }
                        )
                    }
                }
            }
        }

        // App Dock en la parte inferior
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            AppDock(
                apps = apps.take(5),
                onAppClick = { packageName ->
                    launchApp(context, packageName)
                }
            )
        }
    }
}

// Extensión para collectAsStateWithLifecycle
@Composable
private fun <T> androidx.lifecycle.compose.collectAsStateWithLifecycle(
    flow: kotlinx.coroutines.flow.StateFlow<T>
): androidx.compose.runtime.State<T> {
    val context = LocalContext.current
    return androidx.compose.runtime.produceState(
        initialValue = flow.value,
        flow
    ) {
        withContext(Dispatchers.Main.immediate) {
            flow.collect { value = it }
        }
    }
}
