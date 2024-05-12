package ru.testapp.catalog.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.testapp.catalog.viewModel.CartViewModel
import ru.testapp.features.catalog.R
import ru.testapp.models.Product
import ru.testapp.ui.elements.CounterContainer
import ru.testapp.ui.elements.Divider
import ru.testapp.ui.elements.FixedButton
import ru.testapp.ui.elements.NavigateBackButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val cartProducts = viewModel.cartProducts.collectAsState(initial = emptyList()).value
    val commonPriceSum = viewModel.sumPrice.collectAsState(initial = 0).value

    Log.d("CART: ", "$cartProducts")

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Surface(
            shadowElevation = 16.dp,
        ) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .padding(start = 4.dp, top = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    NavigateBackButton(imageId = R.drawable.ic_arrow_back) {
                        navigateUp()
                    }
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.cart_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )
                }
            }
        }

        if (cartProducts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        EmptyScreen(emptyScreenText = stringResource(R.string.empty_cart_text))
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    for (product in cartProducts) {
                        CartProductItem(
                            product = product,
                            cartViewModel = viewModel,
                            cartProducts = cartProducts,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                FixedButton(
                    text = stringResource(R.string.order_for),
                    onClick = {},
                    price = commonPriceSum,
                )
            }
        }
    }
}

@Composable
fun CartProductItem(
    product: Product,
    cartViewModel: CartViewModel,
    cartProducts: List<Product>,
) {

    val productCountInCart = cartProducts.find { it.id == product.id }?.count ?: 0

    var productCounter by rememberSaveable {
        mutableIntStateOf(productCountInCart)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        CartProductItemImage(imageUrl = R.drawable.product_card_image)
        Spacer(modifier = Modifier.width(16.dp))
        CartProductItemInfo(
            product = product,
            onSaveProductToCart = {
                cartViewModel.saveProductToCart(product)
            },
            onRemoveProductFromCart = {
                cartViewModel.removeProductFromCart(product.id)
            },
            onPlusProductCount = {
                productCounter += 1
                product.count = productCounter
            },
            onMinusProductCount = {
                productCounter -= 1
                product.count = productCounter
            },
            onIncreaseSum = {
                cartViewModel.increaseSum(product.priceCurrent)
            },
            onReduceSum = {
                cartViewModel.reduceSum(product.priceCurrent)
            },
            productCount = productCounter,
        )
    }
    Divider()
}

@Composable
fun CartProductItemImage(
    imageUrl: Int = R.drawable.product_card_image,
) {
    Image(
        painter = painterResource(imageUrl),
        contentDescription = null,
        modifier = Modifier.size(width = 96.dp, height = 96.dp),
    )
}

@Composable
fun CartProductItemInfo(
    product: Product,
    onSaveProductToCart: () -> Unit,
    onRemoveProductFromCart: () -> Unit,
    onPlusProductCount: () -> Unit,
    onMinusProductCount: () -> Unit,
    onIncreaseSum: () -> Unit,
    onReduceSum: () -> Unit,
    productCount: Int,
) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
        ) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                modifier = Modifier.alpha(0.87F),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                modifier = Modifier.width(136.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(0.dp),
            ) {
                CounterContainer(
                    counterButtonBackground = Color(0xFFF5F5F5),
                    rowPadding = 0,
                    onMinusClick = onMinusProductCount,
                    onPlusClick = onPlusProductCount,
                    onSaveProductToCart = onSaveProductToCart,
                    onRemoveProductFromCart = onRemoveProductFromCart,
                    onIncreaseSum = onIncreaseSum,
                    onReduceSum = onReduceSum,
                    productCountFromCart = productCount,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .wrapContentSize(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${product.priceCurrent} ₽",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.alpha(0.87F),
                    )
                }

                if (product.priceOld != null) {
                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(modifier = Modifier.wrapContentSize()) {
                            Text(
                                text = "${product.priceOld} ₽",
                                fontSize = 14.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                modifier = Modifier.alpha(0.6F),
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(54.dp)
                                    .height(1.dp)
                                    .offset(y = 11.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(Color.Black, Color.Black)
                                        ), alpha = 0.6f
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}