package ru.testapp.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.testapp.catalog.userInput.EditableUserInputState
import ru.testapp.catalog.userInput.rememberEditableUserInputState
import ru.testapp.catalog.viewModel.CartViewModel
import ru.testapp.catalog.viewModel.CatalogViewModel
import ru.testapp.features.catalog.R
import ru.testapp.ui.elements.NavigateBackButton

@Composable
fun SearchScreen(
    catalogViewModel: CatalogViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {

    val editableUserInputState = rememberEditableUserInputState("")
    var products = catalogViewModel.productsState.collectAsState().value
    val cartProducts = cartViewModel.cartProducts.collectAsState(initial = emptyList()).value

    if (products?.isEmpty() == true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.nothing_found),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(60f),
            )
        }
    }

    if (editableUserInputState.editableInputText == "") {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.enter_goods_name),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(60f),
            )
        }
    }

    Surface(
        shadowElevation = 16.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavigateBackButton(
                    modifier = Modifier.offset(x = 4.dp),
                    imageId = R.drawable.ic_arrow_back,
                    navigateUp = navigateUp,
                )

                ToGoodsInput(
                    onUserInputChanged = { catalogViewModel.toGoodsNameChanged(it) },
                    editableUserInputState = editableUserInputState,
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier.padding(top = 100.dp)
        ) {
            if (editableUserInputState.editableInputText == "") {
                CatalogProductCardsGrid(
                    products = emptyList(),
                    onNavigate = {},
                    cartViewModel = cartViewModel,
                    cartProducts = cartProducts
                )
            } else {
                CatalogProductCardsGrid(
                    products = products,
                    onNavigate = {},
                    cartViewModel = cartViewModel,
                    cartProducts = cartProducts
                )
            }
        }
    }
}

@Composable
fun ToGoodsInput(
    onUserInputChanged: (String) -> Unit,
    editableUserInputState: EditableUserInputState,
) {

    EditableUserInput(
        editableInputState = editableUserInputState,
    )

    val currentOnGoodsNameChanged by rememberUpdatedState(onUserInputChanged)

    LaunchedEffect(editableUserInputState) {
        snapshotFlow { editableUserInputState.editableInputText }
            .collect {
                currentOnGoodsNameChanged(editableUserInputState.editableInputText)
            }
    }
}

@Composable
fun EditableUserInput(
    editableInputState: EditableUserInputState = rememberEditableUserInputState(""),
) {
    AppBaseUserInput(
        onClearInput = {
            editableInputState.clearEditableInput()
        },
        showClearIcon = { editableInputState.editableInputText != "" },
        editableInputState = editableInputState,
    )
}

@Composable
fun AppBaseUserInput(
    editableInputState: EditableUserInputState,
    onClearInput: () -> Unit,
    showClearIcon: () -> Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier.padding(start = 8.dp, end = 24.dp)
        ) {
            TextField(
                value = editableInputState.editableInputText,
                onValueChange = {
                    editableInputState.updateEditableInputText(it)
                },
                placeholder = { Text(text = stringResource(R.string.find_goods)) },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(60f)
                    .offset(x = (-12).dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                trailingIcon = {
                    if (showClearIcon()) {
                        IconButton(
                            onClick = { onClearInput() },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = 12.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clear_input),
                                contentDescription = null,
                            )
                        }
                    }
                }
            )
        }
    }
}