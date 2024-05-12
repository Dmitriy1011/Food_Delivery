package ru.testapp.api

import ru.testapp.models.Category
import ru.testapp.models.LoadResult

interface CategoriesRepository {
    suspend fun getCategories(): LoadResult<List<Category>>
}