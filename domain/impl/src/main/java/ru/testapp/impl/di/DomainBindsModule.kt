package ru.testapp.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.testapp.api.CartUseCase
import ru.testapp.api.GetCategoriesUseCase
import ru.testapp.api.GetProductsUseCase
import ru.testapp.impl.CartUseCaseImpl
import ru.testapp.impl.GetCategoriesUseCaseImpl
import ru.testapp.impl.GetProductsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainBindsModule {

    @Singleton
    @Binds
    fun bindsCategoriesUseCase(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Singleton
    @Binds
    fun bindsProductsUseCase(impl: GetProductsUseCaseImpl): GetProductsUseCase

    @Singleton
    @Binds
    fun bindsCartUseCase(impl: CartUseCaseImpl): CartUseCase
}