package ru.testapp.api

import kotlinx.coroutines.flow.Flow
import ru.testapp.models.Product

interface CartUseCase {
    val cartProducts: Flow<List<Product>>
    val priceSum: Flow<Int>

    suspend fun saveProductToCart(product: Product)
    suspend fun removeProductFromCart(productId: Int)
    suspend fun increasePriceSum(sum: Int)
    suspend fun reducePriceSum(sum: Int)
    suspend fun readCartProductsFromPrefs()
    suspend fun clearPrefs()
}