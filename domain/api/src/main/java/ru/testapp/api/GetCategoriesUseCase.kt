package ru.testapp.api

import ru.testapp.models.Category
import ru.testapp.models.LoadResult

interface GetCategoriesUseCase {
    suspend operator fun invoke(): LoadResult<List<Category>>
}