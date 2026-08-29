package com.hikari.launcher.ui.screens

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AppItem(
    val packageName: String,
    val label: String,
    val icon: android.graphics.drawable.Drawable?
)

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf<List<AppItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            apps = getInstalledApps(context)
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.foundation.background(
                    MaterialTheme.colorScheme.background
                ).brush
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
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
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Apps Grid
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Cargando aplicaciones...",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(apps, key = { it.packageName }) { app ->
                        AppIconView(app = app, context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun AppIconView(app: AppItem, context: Context) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
                .clickable {
                    launchApp(context, app.packageName)
                }
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (app.icon != null) {
                    AsyncImage(
                        model = app.icon,
                        contentDescription = app.label,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            app.label.take(1),
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                }
            }

            Text(
                text = app.label,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 10.sp
            )
        }
    }
}

fun getInstalledApps(context: Context): List<AppItem> {
    val packageManager = context.packageManager
    val apps = mutableListOf<AppItem>()

    try {
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        packages.forEach { app ->
            if (app.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                try {
                    val label = packageManager.getApplicationLabel(app).toString()
                    val icon = packageManager.getApplicationIcon(app)
                    apps.add(
                        AppItem(
                            packageName = app.packageName,
                            label = label,
                            icon = icon
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return apps.sortedBy { it.label }
}

fun launchApp(context: Context, packageName: String) {
    try {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
