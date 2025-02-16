package ua.honchar.ui.mvi.delegate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVI<UiState, UiAction, UiEffect> {
    val uiState: StateFlow<UiState>

    val currentUiState: UiState

    val uiEffect: Flow<UiEffect>

    fun onAction(action: UiAction)

    fun updateUiState(block: UiState.() -> UiState)

    suspend fun emitUiEffect(effect: UiEffect)
}

fun <UiState, UiAction, UiEffect> mvi(
    initialState: UiState,
): MVI<UiState, UiAction, UiEffect> = MVIDelegate(initialState)