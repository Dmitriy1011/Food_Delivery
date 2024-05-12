package ru.testapp.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.testapp.api.CartRepository
import ru.testapp.api.CategoriesRepository
import ru.testapp.api.ProductsRepository
import ru.testapp.impl.repositoryImpl.CartRepositoryImpl
import ru.testapp.impl.repositoryImpl.CategoriesRepositoryImpl
import ru.testapp.impl.repositoryImpl.ProductsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataBindsModule {

    @Binds
    fun bindsCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    fun bindsProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    fun bindsCartRepository(impl: CartRepositoryImpl): CartRepository
}