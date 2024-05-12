package ru.testapp.impl.network

import retrofit2.Response
import retrofit2.http.GET
import ru.testapp.impl.di.BASE_URL
import ru.testapp.models.Category

interface CategoriesApi {
    @GET("Categories.json")
    suspend fun getCategories(): Response<List<Category>>
}