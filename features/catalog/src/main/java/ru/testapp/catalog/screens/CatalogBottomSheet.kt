package ru.testapp.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.testapp.features.catalog.R
import ru.testapp.ui.elements.Divider
import ru.testapp.ui.elements.FixedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: (Boolean) -> Unit,
    showBottomSheetState: Boolean,
    onFilterProducts: (Boolean, Boolean, Boolean) -> Unit,
) {

    var saleState by rememberSaveable {
        mutableStateOf(false)
    }

    var hotState by rememberSaveable {
        mutableStateOf(false)
    }

    var meatState by rememberSaveable {
        mutableStateOf(false)
    }

    if (showBottomSheetState) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet(false)
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
            ) {
                Text(
                    text = stringResource(R.string.choose_dishes_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))

                BottomSheetItem(
                    itemName = stringResource(R.string.for_sale),
                    checkboxValue = saleState,
                    checkBoxChangeState = { newValue ->
                        saleState = newValue
                    },
                )
                BottomSheetItem(
                    itemName = stringResource(R.string.hot),
                    checkboxValue = hotState,
                    checkBoxChangeState = { newValue ->
                        hotState = newValue
                    },
                    divider = { Divider() }
                )
                BottomSheetItem(
                    itemName = stringResource(R.string.without_meat),
                    checkboxValue = meatState,
                    checkBoxChangeState = { newValue ->
                        meatState = newValue
                    }
                )
            }

            FixedButton(
                text = stringResource(R.string.bottom_sheet_button_ready),
                onClick = {
                    onFilterProducts(
                        saleState,
                        hotState,
                        meatState
                    )
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet(false)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomSheetItem(
    modifier: Modifier = Modifier,
    itemName: String,
    checkboxValue: Boolean,
    checkBoxChangeState: (Boolean) -> Unit,
    divider: (@Composable () -> Unit)? = null,
) {

    if (divider != null) {
        divider()
    }

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = itemName
        )
        Checkbox(
            checked = checkboxValue,
            onCheckedChange = {
                checkBoxChangeState(it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFF15412),
            )
        )
    }

    if (divider != null) {
        divider()
    }
}