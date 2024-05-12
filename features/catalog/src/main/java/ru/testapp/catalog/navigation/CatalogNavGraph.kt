package ru.testapp.catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.testapp.catalog.screens.CartScreen
import ru.testapp.catalog.screens.CatalogScreen
import ru.testapp.catalog.screens.ProductInDetailsScreen
import ru.testapp.catalog.screens.SearchScreen

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
) {

    navigation(
        startDestination = AppNavigation.CatalogDestination.route,
        route = AppNavigation.CatalogGraphDestination.route,
    ) {
        composable(route = AppNavigation.CatalogDestination.route) {
            CatalogScreen(
                onCardClick = { productId ->
                    navController.navigateToDetails(productId)
                },
                onFixedButtonClick = {
                    navController.navigateSingleTopTo(AppNavigation.CartDestination.route)
                },
                navigateToSearchScreen = { navController.navigateSingleTopTo(AppNavigation.SearchScreenDestination.route) },
            )
        }
        composable(
            route = "${AppNavigation.ProductInDetailsDestination.route}/{${AppNavigation.ProductInDetailsDestination.argId}}",
            arguments = listOf(
                navArgument(AppNavigation.ProductInDetailsDestination.argId) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val argument =
                entry.arguments?.getString(AppNavigation.ProductInDetailsDestination.argId)?.toInt()
            if (argument != null) {
                ProductInDetailsScreen(
                    productId = argument,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
        composable(route = AppNavigation.CartDestination.route) {
            CartScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = AppNavigation.SearchScreenDestination.route) {
            SearchScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateToDetails(productId: String) {
    this.navigateSingleTopTo("${AppNavigation.ProductInDetailsDestination.route}/${productId}")
}