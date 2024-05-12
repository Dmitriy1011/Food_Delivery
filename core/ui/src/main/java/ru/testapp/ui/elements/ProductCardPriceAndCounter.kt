package ru.testapp.ui.elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.testapp.core.ui.R
import ru.testapp.models.Product

@Composable
fun CounterContainer(
    modifier: Modifier = Modifier,
    counterButtonBackground: Color,
    rowPadding: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onSaveProductToCart: () -> Unit,
    onRemoveProductFromCart: () -> Unit = {},
    onIncreaseSum: () -> Unit = {},
    onReduceSum: () -> Unit = {},
    productCountFromCart: Int,
    paddingBottom: Int? = 0,
    arrangment: Arrangement.HorizontalOrVertical = Arrangement.SpaceBetween,
    spacerBetweenCounterCards: Int? = 0,
) {
    Row(
        horizontalArrangement = arrangment,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = "$rowPadding".toInt().dp)
            .padding(bottom = "$paddingBottom".toInt().dp)
    ) {
        CounterCard(
            resId = R.drawable.icon_minus,
            counterButtonBackground = counterButtonBackground,
            onMinusClick = onMinusClick,
            onPlusClick = {},
            onReduceSum = onReduceSum,
            onSaveProductToCart = onSaveProductToCart,
            onRemoveProductFromCart = onRemoveProductFromCart,
            productCount = productCountFromCart,
        )

        Spacer(modifier = Modifier.width("$spacerBetweenCounterCards".toInt().dp))

        Text(
            text = if (productCountFromCart > 0) {
                "$productCountFromCart"
            } else {
                "1"
            },
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = modifier.alpha(87F)
        )

        Spacer(modifier = Modifier.width("$spacerBetweenCounterCards".toInt().dp))

        CounterCard(
            resId = R.drawable.icon_plus,
            counterButtonBackground = counterButtonBackground,
            onIncreaseSum = onIncreaseSum,
            onPlusClick = onPlusClick,
            onMinusClick = {},
            onSaveProductToCart = onSaveProductToCart,
            productCount = productCountFromCart,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterCard(
    @DrawableRes resId: Int,
    counterButtonBackground: Color,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onSaveProductToCart: () -> Unit = {},
    onRemoveProductFromCart: () -> Unit = {},
    onIncreaseSum: () -> Unit = {},
    onReduceSum: () -> Unit = {},
    productCount: Int,
) {
    Card(
        modifier = Modifier
            .size(width = 40.dp, height = 40.dp),
        onClick = {
            if (resId == R.drawable.icon_minus) {
                if (productCount > 0) {
                    onMinusClick()
                    if (productCount > 1) {
                        onSaveProductToCart()
                    }
                    onReduceSum()
                    if (productCount == 1) {
                        onRemoveProductFromCart()
                    }
                }
            } else {
                onPlusClick()
                onSaveProductToCart()
                onIncreaseSum()
            }
        },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = counterButtonBackground,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (resId == R.drawable.icon_minus) {
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(16.dp)
                        .height(2.dp)
                )
            } else {
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPriceCard(
    product: Product,
    onPlusProductCount: () -> Unit,
    onSaveProductToCart: () -> Unit,
    onIncreaseSum: () -> Unit,
    productCountFromCart: Int,
) {
    if (productCountFromCart == 0) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 16.dp),
            onClick = {
                onPlusProductCount()
                onSaveProductToCart()
                onIncreaseSum()
            },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${product.priceCurrent}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.87f),
                )
                if (product.priceOld != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        Text(
                            text = "${product.priceOld} ₽",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.alpha(0.6f),
                        )
                        Spacer(
                            modifier = Modifier
                                .width(54.dp)
                                .height(1.dp)
                                .offset(y = 13.dp)
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