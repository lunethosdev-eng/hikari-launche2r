package com.hikari.launcher.ui.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikari.launcher.ui.screens.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppListViewModel : ViewModel() {

    private val _apps = MutableStateFlow<List<AppItem>>(emptyList())
    val apps: StateFlow<List<AppItem>> = _apps.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredApps = MutableStateFlow<List<AppItem>>(emptyList())
    val filteredApps: StateFlow<List<AppItem>> = _filteredApps.asStateFlow()

    fun loadApps(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val appList = withContext(Dispatchers.Default) {
                    getInstalledApps(context)
                }
                _apps.value = appList
                _filteredApps.value = appList
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchApps(query: String) {
        _searchQuery.value = query
        _filteredApps.value = if (query.isEmpty()) {
            _apps.value
        } else {
            _apps.value.filter { app ->
                app.label.contains(query, ignoreCase = true) ||
                app.packageName.contains(query, ignoreCase = true)
            }
        }
    }

    fun sortApps(sortBy: SortType) {
        _filteredApps.value = when (sortBy) {
            SortType.NAME -> _filteredApps.value.sortedBy { it.label }
            SortType.INSTALL_TIME -> _filteredApps.value.sortedByDescending { it.installTime }
            SortType.LAST_USED -> _filteredApps.value.sortedByDescending { it.lastUsed }
        }
    }

    private fun getInstalledApps(context: Context): List<AppItem> {
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
                                icon = icon,
                                installTime = app.firstInstallTime,
                                lastUsed = 0L
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
}

enum class SortType {
    NAME,
    INSTALL_TIME,
    LAST_USED
}
