package ru.testapp.impl

import kotlinx.coroutines.flow.Flow
import ru.testapp.api.CartRepository
import ru.testapp.api.CartUseCase
import ru.testapp.models.Product
import javax.inject.Inject

class CartUseCaseImpl @Inject constructor(
    private val repository: CartRepository,
) : CartUseCase {

    override val cartProducts = repository.cartProducts

    override val priceSum: Flow<Int> = repository.priceSum

    override suspend fun saveProductToCart(product: Product) {
        repository.saveProductToCart(product)
    }

    override suspend fun removeProductFromCart(productId: Int) {
        repository.removeProductFromCart(productId)
    }

    override suspend fun readCartProductsFromPrefs() {
        repository.readCartProductsFromPrefs()
    }

    override suspend fun clearPrefs() {
        repository.clearPrefs()
    }

    override suspend fun increasePriceSum(sum: Int) {
        repository.increasePriceSum(sum)
    }

    override suspend fun reducePriceSum(sum: Int) {
        repository.reducePriceSum(sum)
    }
}