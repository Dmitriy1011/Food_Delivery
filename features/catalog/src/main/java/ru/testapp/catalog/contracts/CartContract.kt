package ru.testapp.catalog.contracts

import ru.testapp.models.Product
import ru.testapp.ui.MviEffect
import ru.testapp.ui.MviEvent
import ru.testapp.ui.MviState

object CartContract {

    sealed interface Event : MviEvent

    data class State(
        val cartProducts: List<Product>? = null,
    ) : MviState

    sealed interface Effect : MviEffect {
        data class ErrorMessage(val message: String) : Effect
    }
}