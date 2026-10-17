package com.kawanova.fontstyler.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kawanova.fontstyler.ui.FontStylerScreen
import com.kawanova.fontstyler.ui.FontStylerViewModel
import com.kawanova.fontstyler.ui.PresetStylesScreen
import com.kawanova.fontstyler.ui.SavedStylesScreen

sealed class Screen(val route: String) {
    object Styler : Screen("styler")
    object Presets : Screen("presets")
    object Saved : Screen("saved")
}

@Composable
fun NavGraph(
    viewModel: FontStylerViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Styler.route,
        modifier = modifier
    ) {
        composable(Screen.Styler.route) {
            FontStylerScreen(
                viewModel = viewModel,
                onNavigateToPresets = { navController.navigate(Screen.Presets.route) },
                onNavigateToSaved = { navController.navigate(Screen.Saved.route) }
            )
        }
        composable(Screen.Presets.route) {
            PresetStylesScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Saved.route) {
            SavedStylesScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
