package ru.testapp.catalog.contracts

import ru.testapp.models.Category
import ru.testapp.models.Product
import ru.testapp.models.Tag
import ru.testapp.ui.MviEffect
import ru.testapp.ui.MviEvent
import ru.testapp.ui.MviState

object CatalogContract {

    sealed interface Event : MviEvent

    data class State(
        val categories: List<Category>? = null,
        val tags: List<Tag>? = null,
        var products: List<Product>? = null,
    ) : MviState

    sealed interface Effect : MviEffect {
        data class ErrorMessage(val message: String) : Effect
    }
}