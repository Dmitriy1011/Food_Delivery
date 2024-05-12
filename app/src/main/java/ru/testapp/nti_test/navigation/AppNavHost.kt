package ru.testapp.nti_test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.testapp.catalog.navigation.AppNavigation
import ru.testapp.catalog.navigation.appNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation.CatalogGraphDestination.route,
        modifier = modifier,
    ) {
        appNavGraph(
            navController,
        )
    }
}