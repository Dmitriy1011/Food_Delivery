package ru.testapp.impl.repositoryImpl

import ru.testapp.api.CategoriesRepository
import ru.testapp.impl.network.CategoriesApi
import ru.testapp.models.Category
import ru.testapp.models.LoadResult
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi,
) : CategoriesRepository {

    override suspend fun getCategories(): LoadResult<List<Category>> {
        val response = categoriesApi.getCategories()
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