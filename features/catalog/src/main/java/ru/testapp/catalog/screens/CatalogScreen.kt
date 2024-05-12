package ru.testapp.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.testapp.catalog.navigation.NavigationTags
import ru.testapp.catalog.viewModel.CartViewModel
import ru.testapp.catalog.viewModel.CatalogViewModel
import ru.testapp.features.catalog.R
import ru.testapp.models.Category
import ru.testapp.models.Product
import ru.testapp.ui.elements.CounterContainer
import ru.testapp.ui.elements.FiltersCounter
import ru.testapp.ui.elements.FixedButton
import ru.testapp.ui.elements.ProductPriceCard
import ru.testapp.ui.elements.TabEmptyDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onCardClick: (String) -> Unit,
    onFixedButtonClick: () -> Unit,
    catalogViewModel: CatalogViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val products = catalogViewModel.productsState.collectAsState().value
    val categories = catalogViewModel.state.value.categories
    val filtersCount = catalogViewModel.filtersCounter.collectAsState().value
    val sumPrice = cartViewModel.sumPrice.collectAsState(initial = 0).value

    val cartProducts = cartViewModel.cartProducts.collectAsState(emptyList()).value

    if (catalogViewModel.state.value.products?.isEmpty() == true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag(NavigationTags.catalogEmptyScreen)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                EmptyScreen(
                    emptyScreenText = stringResource(R.string.empty_catalog_text)
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CatalogToolBar(
            navigateToSearchScreen = navigateToSearchScreen,
            onShowBottomSheet = {
                showBottomSheet = true
            },
            filtersCount = filtersCount,
        )
        Spacer(modifier = Modifier.height(8.dp))


        Spacer(modifier = Modifier.height(16.dp))

        if (categories != null) {
            Tabs(categories = categories)
        }

        Box(modifier = Modifier.weight(1f)) {
            CatalogProductCardsGrid(
                products = products,
                onNavigate = onCardClick,
                cartViewModel = cartViewModel,
                cartProducts = cartProducts,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        FixedButton(
            icon = R.drawable.ic_cart,
            price = sumPrice,
            onClick = onFixedButtonClick,
        )


        CatalogBottomSheet(
            sheetState = sheetState,
            scope = scope,
            showBottomSheet = { newValue -> showBottomSheet = newValue },
            showBottomSheetState = showBottomSheet,
            onFilterProducts = { sale, hot, meat ->
                catalogViewModel.filterProductsGrid(
                    saleState = sale,
                    hotState = hot,
                    meatState = meat,
                )
            }
        )
    }
}

@Composable
fun CatalogToolBar(
    navigateToSearchScreen: () -> Unit,
    onShowBottomSheet: () -> Unit,
    filtersCount: Int,
) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,

        ) {

        Box {
            IconButton(
                onClick = {}
            ) {
                IconButton(onClick = onShowBottomSheet) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null
                    )
                }
            }
            if (filtersCount > 0) {
                FiltersCounter(
                    count = filtersCount,
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.catalog_logo),
            contentDescription = null,
        )

        IconButton(
            onClick = navigateToSearchScreen
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_icon),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Tabs(
    categories: List<Category>?,
) {
    var tabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = tabIndex,
        divider = { TabEmptyDivider() },
        edgePadding = 16.dp,
        indicator = {
            TabRowDefaults.Indicator(
                color = Color.Transparent
            )
        }
    ) {
        categories?.forEachIndexed { index, category ->
            Tab(
                modifier = Modifier
                    .background(
                        color = if (tabIndex == index) {
                            Color(0xFFF15412)
                        } else {
                            Color.Transparent
                        },
                        shape = RoundedCornerShape(8.dp)
                    ),
                text = {
                    Text(
                        text = category.name,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (tabIndex == index) {
                            Color.White
                        } else {
                            Color.Black
                        },
                    )
                },
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                }
            )
        }
    }
}

@Composable
fun CatalogProductCardsGrid(
    products: List<Product>?,
    cartProducts: List<Product>,
    onNavigate: (String) -> Unit,
    cartViewModel: CartViewModel,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (products != null) {
            items(items = products) { productItem ->
                CatalogProductCard(
                    product = productItem,
                    onNavigate = onNavigate,
                    cartViewModel = cartViewModel,
                    cartProducts = cartProducts,
                )
            }
        }
    }
}

@Composable
fun CatalogProductCard(
    product: Product,
    onNavigate: (String) -> Unit,
    cartProducts: List<Product>,
    cartViewModel: CartViewModel,
) {
    var productCountFromCart by rememberSaveable {
        mutableIntStateOf(0)
    }

    cartProducts.forEach {
        if (it.id == product.id) {
            productCountFromCart = it.count
        }
    }

    if (cartProducts.isEmpty()) {
        productCountFromCart = 0
    }
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(292.dp)
            .heightIn(max = 292.dp)
            .clickable(
                onClick = { onNavigate(product.id.toString()) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFf5f5f5),
        )
    ) {
        Column {
            Box {
                if (product.priceOld != null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_tag),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = 8.dp, y = 8.dp),
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.product_card_image),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .widthIn(max = 150.dp),
                text = product.name,
                fontSize = 14.sp,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = buildString {
                    append(product.measure)
                    append(" ")
                    append(product.measureUnit)
                    append("г")
                },
                modifier = Modifier
                    .alpha(60F)
                    .padding(start = 16.dp),
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))

            ProductPriceCard(
                product = product,
                onPlusProductCount = {
                    productCountFromCart += 1
                    product.count = productCountFromCart
                },
                onSaveProductToCart = {
                    cartViewModel.saveProductToCart(product)
                },
                onIncreaseSum = {
                    cartViewModel.increaseSum(product.priceCurrent)
                },
                productCountFromCart = productCountFromCart,
            )

            if (productCountFromCart > 0) {
                CounterContainer(
                    counterButtonBackground = Color.White,
                    rowPadding = 8,
                    onPlusClick = {
                        productCountFromCart += 1
                        product.count = productCountFromCart
                    },
                    onMinusClick = {
                        productCountFromCart -= 1
                        product.count = productCountFromCart
                    },
                    onIncreaseSum = {
                        cartViewModel.increaseSum(product.priceCurrent)
                    },
                    onReduceSum = {
                        cartViewModel.reduceSum(product.priceCurrent)
                    },
                    onSaveProductToCart = {
                        cartViewModel.saveProductToCart(product)
                    },
                    onRemoveProductFromCart = {
                        cartViewModel.removeProductFromCart(product.id)
                    },
                    productCountFromCart = productCountFromCart
                )
            }
        }
    }
}
