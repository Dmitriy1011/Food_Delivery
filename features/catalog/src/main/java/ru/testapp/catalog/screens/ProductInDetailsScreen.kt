package ru.testapp.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.testapp.catalog.viewModel.CartViewModel
import ru.testapp.catalog.viewModel.CatalogViewModel
import ru.testapp.features.catalog.R
import ru.testapp.models.Product
import ru.testapp.ui.elements.CounterContainer
import ru.testapp.ui.elements.Divider
import ru.testapp.ui.elements.FixedButton
import ru.testapp.ui.elements.NavigateBackButton

@Composable
fun ProductInDetailsScreen(
    productId: Int,
    viewModel: CatalogViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val cart = cartViewModel.cartProducts.collectAsState(initial = emptyList()).value

    var productCounter by rememberSaveable {
        mutableIntStateOf(0)
    }

    cart.forEach {
        if (it.id == productId) {
            productCounter = it.count
        }
    }

    viewModel.state.value.products?.forEach { product ->
        if (product.id == productId) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    NavigateBackButton(
                        modifier = Modifier.offset(x = 4.dp, y = 8.dp),
                        imageId = R.drawable.ic_arrow_back
                    ) {
                        navigateUp()
                    }

                    Image(
                        painter = painterResource(id = R.drawable.product_in_details_image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(375.dp)
                    )
                }
                ProductInDetailsTitle(title = product.name)
                Spacer(modifier = Modifier.height(8.dp))
                ProductInDetailsDescr(descr = product.description)
                Spacer(modifier = Modifier.height(16.dp))
                ProductNutritionalValues(product = product)
                Spacer(modifier = Modifier.height(24.dp))

                if (productCounter == 0) {
                    FixedButton(
                        price = product.priceCurrent,
                        text = stringResource(R.string.in_cart_with_price),
                        onClick = {
                            productCounter += 1
                            product.count = productCounter
                            cartViewModel.saveProductToCart(product)
                            cartViewModel.increaseSum(product.priceCurrent)
                        },
                    )
                } else {
                    CounterContainer(
                        arrangment = Arrangement.Center,
                        spacerBetweenCounterCards = 30,
                        counterButtonBackground = Color.White,
                        rowPadding = 0,
                        paddingBottom = 16,
                        onPlusClick = {
                            productCounter += 1
                            product.count = productCounter
                        },
                        onMinusClick = {
                            productCounter -= 1
                            product.count = productCounter
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
                        productCountFromCart = productCounter
                    )
                }
            }
        }
    }
}

@Composable
fun ProductInDetailsTitle(
    title: String,
) {
    Text(
        text = title,
        fontSize = 34.sp,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp,
        color = Color.Black,
        modifier = Modifier
            .alpha(0.87f)
            .padding(horizontal = 16.dp),
    )
}

@Composable
fun ProductInDetailsDescr(
    descr: String,
) {
    Text(
        text = descr,
        fontSize = 16.sp,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        color = Color.Black,
        modifier = Modifier
            .alpha(0.6f)
            .padding(horizontal = 16.dp),
    )
}

@Composable
fun ProductNutritionalValues(
    product: Product?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Divider()
        ProductNutritionalValueCard(
            valueTitle = "Вес", valueDescr = "${product?.measure} ${product?.measureUnit}"
        )
        Divider()
        ProductNutritionalValueCard(
            valueTitle = "Энерг.ценность",
            valueDescr = "${product?.energyPer100Grams} ккал"
        )
        Divider()
        ProductNutritionalValueCard(
            valueTitle = "Белки",
            valueDescr = "${product?.proteinsPer100Grams} ${product?.measureUnit}"
        )
        Divider()
        ProductNutritionalValueCard(
            valueTitle = "Жиры", valueDescr = "${product?.fatsPer100Grams} ${product?.measureUnit}"
        )
        Divider()
        ProductNutritionalValueCard(
            valueTitle = "Углеводы",
            valueDescr = "${product?.carbohydratesPer100Grams} ${product?.measureUnit}"
        )
        Divider()
    }
}

@Composable
fun ProductNutritionalValueCard(
    valueTitle: String,
    valueDescr: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = valueTitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .alpha(0.6F)
                    .weight(1F),
            )

            Text(
                text = valueDescr,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .alpha(0.87F)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductInDetailsTitlePreview() {
    ProductInDetailsTitle(title = "Том Ям")
}

@Preview(showBackground = true)
@Composable
fun ProductInDetailsDescrPreview() {
    ProductInDetailsDescr(descr = "Кокосовое молоко, кальмары, креветки помидоры черри, грибы вешанки")
}

@Preview(showBackground = true)
@Composable
fun ProductNutritionalValuesPreview() {
    ProductNutritionalValues(product = null)
}