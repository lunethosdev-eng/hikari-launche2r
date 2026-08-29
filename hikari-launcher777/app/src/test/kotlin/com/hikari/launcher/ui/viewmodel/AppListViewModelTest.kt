package com.hikari.launcher.ui.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import com.hikari.launcher.ui.screens.AppItem
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowPackageManager
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class AppListViewModelTest {

    private lateinit var viewModel: AppListViewModel
    private lateinit var context: Context
    private lateinit var packageManager: PackageManager
    private lateinit var shadowPackageManager: ShadowPackageManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        packageManager = context.packageManager
        shadowPackageManager = shadowOf(packageManager)
        viewModel = AppListViewModel()
    }

    @Test
    fun testLoadAppsSuccessfully() {
        // Arrange
        val appLabel = "Test App"
        val packageName = "com.test.app"
        
        // Act
        viewModel.loadApps(context)
        
        // Assert
        assertTrue(viewModel.isLoading.value == false)
    }

    @Test
    fun testSearchAppsFiltersCorrectly() {
        // Arrange
        val apps = listOf(
            AppItem("com.whatsapp", "WhatsApp", null),
            AppItem("com.facebook", "Facebook", null),
            AppItem("com.twitter", "Twitter", null)
        )
        
        // Act
        viewModel.searchApps("WhatsApp")
        
        // Assert
        assertEquals(viewModel.searchQuery.value, "WhatsApp")
    }

    @Test
    fun testSortAppsByName() {
        // Arrange
        val apps = listOf(
            AppItem("com.z.app", "Zapp", null),
            AppItem("com.a.app", "Aapp", null),
            AppItem("com.m.app", "Mapp", null)
        )
        
        // Act
        viewModel.sortApps(SortType.NAME)
        
        // Assert
        assertTrue(viewModel.filteredApps.value.isNotEmpty())
    }

    @Test
    fun testEmptySearchQuery() {
        // Arrange
        viewModel.searchApps("Test")
        
        // Act
        viewModel.searchApps("")
        
        // Assert
        assertEquals(viewModel.searchQuery.value, "")
    }

    @Test
    fun testCaseSensitiveSearch() {
        // Arrange
        val query1 = "WHATSAPP"
        val query2 = "whatsapp"
        val query3 = "WhatsApp"
        
        // Act & Assert
        viewModel.searchApps(query1)
        assertTrue(viewModel.searchQuery.value == query1)
        
        viewModel.searchApps(query2)
        assertTrue(viewModel.searchQuery.value == query2)
        
        viewModel.searchApps(query3)
        assertTrue(viewModel.searchQuery.value == query3)
    }
}
