package com.hikari.launcher.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hikari_preferences")

class LauncherPreferences(private val context: Context) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val GRID_COLUMNS = intPreferencesKey("grid_columns")
        val APP_ANIMATION_SPEED = intPreferencesKey("animation_speed")
        val THEME_COLOR = stringPreferencesKey("theme_color")
        val SHOW_NOTIFICATION_BADGE = booleanPreferencesKey("show_badge")
        val HIDE_SYSTEM_APPS = booleanPreferencesKey("hide_system_apps")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { it[DARK_MODE] ?: false }

    val gridColumnsFlow: Flow<Int> = context.dataStore.data
        .map { it[GRID_COLUMNS] ?: 4 }

    val animationSpeedFlow: Flow<Int> = context.dataStore.data
        .map { it[APP_ANIMATION_SPEED] ?: 300 }

    val themeColorFlow: Flow<String> = context.dataStore.data
        .map { it[THEME_COLOR] ?: "#2196F3" }

    val showNotificationBadgeFlow: Flow<Boolean> = context.dataStore.data
        .map { it[SHOW_NOTIFICATION_BADGE] ?: true }

    val hideSystemAppsFlow: Flow<Boolean> = context.dataStore.data
        .map { it[HIDE_SYSTEM_APPS] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun setGridColumns(columns: Int) {
        context.dataStore.edit { it[GRID_COLUMNS] = columns }
    }

    suspend fun setAnimationSpeed(speed: Int) {
        context.dataStore.edit { it[APP_ANIMATION_SPEED] = speed }
    }

    suspend fun setThemeColor(color: String) {
        context.dataStore.edit { it[THEME_COLOR] = color }
    }
}

private suspend fun <T> (DataStore<Preferences>).edit(block: suspend (MutableMap<Preferences.Key<*>, Any>) -> Unit) {
    // Implementation needed
}
