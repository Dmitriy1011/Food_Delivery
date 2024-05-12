package ru.testapp.catalog.navigation

sealed class AppNavigation(
    val route: String,
) {

    data object CatalogGraphDestination : AppNavigation(
        route = "CatalogGraphDestination",
    )

    data object CatalogDestination : AppNavigation(
        route = "CatalogDestination",
    )

    data object ProductInDetailsDestination : AppNavigation(
        route = "ProductInDetailsNavigation",
    ) {

        val argId: String = "product_id"
    }

    data object CartDestination : AppNavigation(
        route = "CartDestination",
    ) {

        val title = "Корзина"
    }

    data object SearchScreenDestination : AppNavigation(
        route = "SearchScreenDest"
    )

    companion object {

        val screens = listOf(
            CatalogDestination,
            ProductInDetailsDestination,
            CartDestination,
            SearchScreenDestination
        )
    }
}