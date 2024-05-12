package ru.testapp.catalog.userInput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class EditableUserInputState(
    private val initialText: String,
) {

    var editableInputText by mutableStateOf(initialText)
        private set

    fun updateEditableInputText(newText: String) {
        editableInputText = newText
    }

    fun clearEditableInput() {
        editableInputText = ""
    }

    companion object {

        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it.initialText) },
            restore = {
                EditableUserInputState(
                    initialText = it[0]
                )
            }
        )
    }
}

@Composable
fun rememberEditableUserInputState(initialText: String): EditableUserInputState =
    rememberSaveable(initialText, saver = EditableUserInputState.Saver) {
        EditableUserInputState(initialText)
    }
