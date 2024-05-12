package ru.testapp.api

import ru.testapp.models.LoadResult
import ru.testapp.models.Product

interface GetProductsUseCase {
    suspend operator fun invoke(): LoadResult<List<Product>>
}