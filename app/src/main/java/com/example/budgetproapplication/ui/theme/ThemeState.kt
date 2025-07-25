package com.example.budgetproapplication.ui.theme

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

// DataStore instance for theme preferences
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

/**
 * Manages the theme state for the application.
 * 
 * @property context The application context
 * @property initialDarkMode The initial dark mode state
 */
@Stable
class ThemeState(
    private val context: Context,
    initialDarkMode: Boolean = false
) {
    private val darkModeKey = booleanPreferencesKey("dark_theme")
    
    // Mutable state for dark mode
    var isDark by mutableStateOf(initialDarkMode)
        private set
    
    /**
     * Update the theme state
     */
    suspend fun updateTheme(dark: Boolean) {
        isDark = dark
        saveThemePreference(dark)
    }
    
    /**
     * Toggle the current theme between dark and light mode
     */
    suspend fun toggleTheme() {
        updateTheme(!isDark)
    }
    
    /**
     * Save the theme preference to DataStore
     */
    private suspend fun saveThemePreference(enabled: Boolean) {
        try {
            context.dataStore.edit { preferences ->
                preferences[darkModeKey] = enabled
            }
        } catch (e: IOException) {
            // Handle error saving preference
            e.printStackTrace()
        }
    }
    
    /**
     * Load the saved theme preference from DataStore
     */
    suspend fun loadThemePreference(): Boolean {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[darkModeKey] ?: false
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}

/**
 * Remember the theme state across recompositions
 */
@Composable
fun rememberThemeState(
    context: Context = LocalContext.current
): ThemeState {
    val themeState = remember {
        ThemeState(context)
    }
    
    // Load the saved theme preference when the composable is first launched
    LaunchedEffect(Unit) {
        themeState.updateTheme(themeState.loadThemePreference())
    }
    
    return themeState
}
