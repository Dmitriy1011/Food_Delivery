package ru.testapp.catalog.viewModel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.testapp.api.GetCategoriesUseCase
import ru.testapp.api.GetProductsUseCase
import ru.testapp.catalog.contracts.CatalogContract.Effect
import ru.testapp.catalog.contracts.CatalogContract.Event
import ru.testapp.catalog.contracts.CatalogContract.State
import ru.testapp.models.LoadResult
import ru.testapp.models.Product
import ru.testapp.ui.MviViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getProducts: GetProductsUseCase,
) : MviViewModel<Event, State, Effect>() {

    override fun setInitialState() = State()

    init {
        loadCategories()
        loadProducts()
    }

    private var initialProducts = mutableListOf<Product>()

    private val _productsState = MutableStateFlow<List<Product>?>(emptyList())
    val productsState: StateFlow<List<Product>?> = _productsState.asStateFlow()

    private val _filtersCounter = MutableStateFlow(0)
    val filtersCounter = _filtersCounter.asStateFlow()

    private val checkBoxesChecked = mutableListOf(false, false, false)

    private fun loadCategories() = viewModelScope.launch {
        when (val result = getCategories()) {
            is LoadResult.Done -> {
                setState { it.copy(categories = result.result) }
            }

            is LoadResult.Error -> setEffect { Effect.ErrorMessage(result.message) }

            LoadResult.Loading -> {}

            else -> {}
        }
    }

    fun loadProducts() = viewModelScope.launch {
        when (val result = getProducts()) {
            is LoadResult.Done -> {

                setState { it.copy(products = result.result) }
                _productsState.update { state.value.products }
                state.value.products?.let { initialProducts.addAll(it) }
            }

            is LoadResult.Error -> setEffect { Effect.ErrorMessage(result.message) }

            LoadResult.Loading -> {}

            else -> {}
        }
    }

    fun toGoodsNameChanged(goodsName: String) = viewModelScope.launch {
        val filteredProducts = state.value.products?.filter {
            it.name.lowercase().contains(goodsName.lowercase())
        }

        _productsState.update { filteredProducts }
    }

    fun filterProductsGrid(
        saleState: Boolean,
        hotState: Boolean,
        meatState: Boolean,
    ) = viewModelScope.launch {

        _productsState.value = initialProducts

        val word = "остры"

        val saleProducts = productsState.value?.filter { product ->
            product.priceOld != null
        }

        if (!checkBoxesChecked[0]) {
            if (saleState) {
                filterProducts(saleProducts)
                checkBoxesChecked[0] = true
                _filtersCounter.value += 1
            }
        } else {
            if (!saleState) {
                checkBoxesChecked[0] = false
                _filtersCounter.value -= 1
            } else {
                filterProducts(saleProducts)
            }
        }

        val hotProducts = productsState.value?.filter { product ->
            product.description.lowercase().contains(word)
        }

        if (!checkBoxesChecked[1]) {
            if (hotState) {
                filterProducts(hotProducts)
                checkBoxesChecked[1] = true
                _filtersCounter.value += 1
            }
        } else {
            if (!hotState) {
                checkBoxesChecked[1] = false
                _filtersCounter.value -= 1
            } else {
                filterProducts(hotProducts)
            }
        }

        val meatProducts = productsState.value?.filter { product ->
            !product.description.lowercase().contains("куриц")
                    && !product.description.lowercase().contains("говя")
                    && !product.description.lowercase().contains("свин")
        }

        if (!checkBoxesChecked[2]) {
            if (meatState) {
                filterProducts(meatProducts)
                checkBoxesChecked[2] = true
                _filtersCounter.value += 1
            }
        } else {
            if (!meatState) {
                checkBoxesChecked[2] = false
                _filtersCounter.value -= 1
            } else {
                filterProducts(meatProducts)
            }
        }
    }

    private fun filterProducts(filteredProducts: List<Product>?) {
        _productsState.value = filteredProducts
    }
}