package com.hikari.launcher.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hikari.launcher.data.models.AppItem
import com.hikari.launcher.data.models.SortType
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppListViewModelTest {

    private lateinit var viewModel: AppListViewModel

    @Before
    fun setUp() {
        viewModel = AppListViewModel()
    }

    @Test
    fun testInitialState() {
        assertTrue(viewModel.apps.value.isEmpty())
        assertEquals(viewModel.filteredApps.value, viewModel.apps.value)
        assertEquals(viewModel.searchQuery.value, "")
    }

    @Test
    fun testSearchQueryUpdates() {
        viewModel.searchApps("Test")
        assertEquals(viewModel.searchQuery.value, "Test")
    }

    @Test
    fun testEmptySearchQueryResetsFilter() {
        viewModel.searchApps("Test")
        viewModel.searchApps("")
        assertEquals(viewModel.searchQuery.value, "")
        assertEquals(viewModel.filteredApps.value, viewModel.apps.value)
    }

    @Test
    fun testSearchFiltersByLabel() {
        // Simula apps internamente poniendo el StateFlow
        val field = AppListViewModel::class.java.getDeclaredField("_apps")
        field.isAccessible = true
        val flow = field.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<List<AppItem>>
        flow.value = listOf(
            AppItem(packageName = "com.whatsapp", label = "WhatsApp"),
            AppItem(packageName = "com.facebook", label = "Facebook")
        )

        viewModel.searchApps("whats")
        assertEquals(1, viewModel.filteredApps.value.size)
        assertEquals("WhatsApp", viewModel.filteredApps.value.first().label)
    }

    @Test
    fun testSearchFiltersByPackageName() {
        val field = AppListViewModel::class.java.getDeclaredField("_apps")
        field.isAccessible = true
        val flow = field.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<List<AppItem>>
        flow.value = listOf(
            AppItem(packageName = "com.example.app", label = "Ejemplo"),
            AppItem(packageName = "org.other.app", label = "Otro")
        )

        viewModel.searchApps("com.example")
        assertEquals(1, viewModel.filteredApps.value.size)
        assertEquals("Ejemplo", viewModel.filteredApps.value.first().label)
    }

    @Test
    fun testSortByName() {
        val field = AppListViewModel::class.java.getDeclaredField("_filteredApps")
        field.isAccessible = true
        val flow = field.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<List<AppItem>>
        flow.value = listOf(
            AppItem(packageName = "com.z", label = "Zebra"),
            AppItem(packageName = "com.a", label = "Apple"),
            AppItem(packageName = "com.m", label = "Mango")
        )

        viewModel.sortApps(SortType.NAME)
        val labels = viewModel.filteredApps.value.map { it.label }
        assertEquals(listOf("Apple", "Mango", "Zebra"), labels)
    }
}
