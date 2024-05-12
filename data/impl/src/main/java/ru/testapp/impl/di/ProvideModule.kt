package ru.testapp.impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.testapp.impl.network.CategoriesApi
import ru.testapp.impl.network.ProductsApi
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

const val BASE_URL = "https://anika1d.github.io/WorkTestServer/"

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Singleton
    @Provides
    fun providesLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton

    @Provides
    fun providesOkttp(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun providesCategoryApi(retrofit: Retrofit): CategoriesApi = retrofit.create<CategoriesApi>()

    @Singleton
    @Provides
    fun providesProductsApi(retrofit: Retrofit): ProductsApi = retrofit.create<ProductsApi>()

    @Singleton
    @Provides
    fun providesContext(
        @ApplicationContext
        context: Context
    ): Context = context

    @Singleton
    @Provides
    fun providesCoroutineScope(coroutineContext: CoroutineContext): CoroutineContext = coroutineContext
}