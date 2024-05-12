package ru.testapp.catalog.userInput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

class CheckBoxFilterState(
    private val initialSaleState: Boolean,
    private val initialHotState: Boolean,
    private val initialWithoutMeatState: Boolean,
) {

    var editableSaleState by mutableStateOf(initialSaleState)

    var editableHotState by mutableStateOf(initialHotState)

    var editableWithoutMeatState by mutableStateOf(initialWithoutMeatState)

    companion object {

        val Saver: Saver<CheckBoxFilterState, *> = listSaver(
            save = {
                listOf(
                    it.editableSaleState,
                    it.editableHotState,
                    it.editableWithoutMeatState
                )
            },
            restore = {
                CheckBoxFilterState(
                    initialSaleState = it[0],
                    initialHotState = it[1],
                    initialWithoutMeatState = it[2],
                )
            }
        )
    }
}

@Composable
fun rememberCheckBoxFilterState(
    initialSaleState: Boolean,
    initialHotState: Boolean,
    initialWithoutMeatState: Boolean,
): CheckBoxFilterState =
    remember(
        initialSaleState,
        initialHotState,
        initialWithoutMeatState,
        CheckBoxFilterState.Saver
    ) {
        CheckBoxFilterState(initialSaleState, initialHotState, initialWithoutMeatState)
    }