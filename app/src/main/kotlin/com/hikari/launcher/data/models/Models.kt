package com.hikari.launcher.data.models

import android.graphics.drawable.Drawable

data class AppItem(
    val packageName: String,
    val label: String,
    val icon: Drawable? = null,
    val installTime: Long = 0L,
    val lastUsed: Long = 0L,
    val category: AppCategory = AppCategory.OTHER,
    val isFavorite: Boolean = false,
    val isVisible: Boolean = true,
    val customColor: String? = null,
    val customLabel: String? = null
) {
    fun getDisplayLabel(): String = customLabel ?: label
}

enum class AppCategory(val displayName: String) {
    SOCIAL("Social"),
    PRODUCTIVITY("Productividad"),
    ENTERTAINMENT("Entretenimiento"),
    GAMES("Juegos"),
    TOOLS("Herramientas"),
    COMMUNICATION("Comunicación"),
    EDUCATION("Educación"),
    HEALTH("Salud"),
    SHOPPING("Compras"),
    OTHER("Otro")
}

data class LauncherSettings(
    val gridColumns: Int = 4,
    val animationSpeed: Int = 300,
    val darkMode: Boolean = false,
    val themeColor: String = "#2196F3",
    val hideSystemApps: Boolean = false,
    val showNotificationBadge: Boolean = true,
    val dockVisible: Boolean = true,
    val searchBarVisible: Boolean = true,
    val appAnimationEnabled: Boolean = true,
    val sortType: SortType = SortType.NAME,
    val categorizeApps: Boolean = false
)

enum class SortType(val displayName: String) {
    NAME("Nombre"),
    INSTALL_TIME("Instalación"),
    LAST_USED("Último uso"),
    SIZE("Tamaño")
}

data class AppShortcut(
    val id: String,
    val packageName: String,
    val label: String,
    val icon: Drawable? = null,
    val position: Int = 0
)
