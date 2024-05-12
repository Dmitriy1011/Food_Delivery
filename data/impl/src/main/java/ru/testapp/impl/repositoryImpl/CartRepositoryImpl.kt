package ru.testapp.impl.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.testapp.api.CartRepository
import ru.testapp.models.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val context: Context,
) : CartRepository {

    //Init DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("CartProducts")
    private val productsKey = stringPreferencesKey("PRODUCTS_ID")

    //Gson
    private val gson = Gson()
    private val type = TypeToken.getParameterized(MutableList::class.java, Product::class.java).type

    private var products = mutableListOf<Product>()

    private val _priceSum = MutableStateFlow(0)
    override val priceSum: Flow<Int> = _priceSum.asStateFlow()

    private val _cartProducts = MutableStateFlow(products)
    override val cartProducts = _cartProducts.asStateFlow()

    override suspend fun saveProductToCart(product: Product) {

        sync()
        readCartProductsFromPrefs()

        if (products.isEmpty()) {
            products.add(product)
        } else {
            products = products.map {
                if (it.id == product.id) it.copy(count = product.count) else it
            }.toMutableList()

            if (!products.contains(product)) {
                products.add(product)
            }
        }

        sync()
        readCartProductsFromPrefs()
    }

    override suspend fun removeProductFromCart(productId: Int) {
        products = products.filter {
            it.id != productId
        }.toMutableList()

        sync()
        readCartProductsFromPrefs()
    }

    private suspend fun sync() {
        context.dataStore.edit { prefs ->
            prefs[productsKey] = gson.toJson(products)
        }
    }

    override suspend fun readCartProductsFromPrefs() {
        context.dataStore.edit { prefs ->
            products = gson.fromJson(prefs[productsKey], type)
            _cartProducts.update { products }
        }
    }

    override suspend fun clearPrefs() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    override suspend fun increasePriceSum(sum: Int) {
        _priceSum.value += sum
    }

    override suspend fun reducePriceSum(sum: Int) {
        _priceSum.value -= sum
    }
}

