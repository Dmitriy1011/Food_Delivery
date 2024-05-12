package ru.testapp.impl.network

import retrofit2.Response
import retrofit2.http.GET
import ru.testapp.impl.di.BASE_URL
import ru.testapp.models.Product

interface ProductsApi {
    @GET("Products.json")
    suspend fun getProducts(): Response<List<Product>>
}