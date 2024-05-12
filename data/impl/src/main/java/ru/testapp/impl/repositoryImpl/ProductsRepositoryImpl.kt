package ru.testapp.impl.repositoryImpl

import ru.testapp.api.ProductsRepository
import ru.testapp.impl.network.ProductsApi
import ru.testapp.models.LoadResult
import ru.testapp.models.Product
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
) : ProductsRepository {

    override suspend fun getProducts(): LoadResult<List<Product>> {
        val response = productsApi.getProducts()
        return if (response.isSuccessful) {
            if (response.body() != null) {
                LoadResult.Done(response.body())
            } else {
                LoadResult.Error("Body is null")
            }
        } else {
            LoadResult.Error("Unsuccessful response; code: ${response.code()} message: ${response.message()}")
        }
    }
}