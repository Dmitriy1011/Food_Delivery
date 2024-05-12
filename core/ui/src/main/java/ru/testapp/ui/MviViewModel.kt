package ru.testapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MviEvent

interface MviState

interface MviEffect

abstract class MviViewModel<Event : MviEvent, State : MviState, Effect : MviEffect> : ViewModel() {

    private val initialState: State by lazy { setInitialState() }

    private val _state: MutableState<State> = mutableStateOf(initialState)
    val state: androidx.compose.runtime.State<State> = _state

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    abstract fun setInitialState(): State

//    init {
//        subscribeToEvents()
//    }

//    abstract fun handleEvents(event: Event)
//
//    private fun subscribeToEvents() {
//        viewModelScope.launch {
//            _event.collect {
//                handleEvents(it)
//            }
//        }
//    }

    protected fun setState(reduce: (State) -> State) {
        val newState = reduce(state.value)
        _state.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}