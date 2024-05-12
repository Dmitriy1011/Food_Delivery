package ru.testapp.catalog.viewModel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.testapp.api.CartUseCase
import ru.testapp.catalog.contracts.CartContract.Effect
import ru.testapp.catalog.contracts.CartContract.Event
import ru.testapp.catalog.contracts.CartContract.State
import ru.testapp.models.Product
import ru.testapp.ui.MviViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cart: CartUseCase,
) : MviViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    val cartProducts = cart.cartProducts
    val sumPrice = cart.priceSum

    fun saveProductToCart(product: Product) = viewModelScope.launch {
        cart.saveProductToCart(product)
    }

    fun removeProductFromCart(productId: Int) = viewModelScope.launch {
        cart.removeProductFromCart(productId)
    }

    fun increaseSum(sum: Int) = viewModelScope.launch {
        cart.increasePriceSum(sum)
    }

    fun reduceSum(sum: Int) = viewModelScope.launch {
        cart.reducePriceSum(sum)
    }
}