package ru.testapp.impl

import ru.testapp.api.GetProductsUseCase
import ru.testapp.api.ProductsRepository
import ru.testapp.models.LoadResult
import ru.testapp.models.Product
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: ProductsRepository,
) : GetProductsUseCase {

    override suspend fun invoke(): LoadResult<List<Product>> {
        return repository.getProducts()
    }
}