package ru.testapp.impl

import ru.testapp.api.CategoriesRepository
import ru.testapp.api.GetCategoriesUseCase
import ru.testapp.models.Category
import ru.testapp.models.LoadResult
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(
    private val repository: CategoriesRepository,
) : GetCategoriesUseCase {

    override suspend fun invoke(): LoadResult<List<Category>> {
        return repository.getCategories()
    }
}