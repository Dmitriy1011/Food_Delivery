package ru.testapp.api

import ru.testapp.models.LoadResult
import ru.testapp.models.Product

interface ProductsRepository {
    suspend fun getProducts(): LoadResult<List<Product>>
}