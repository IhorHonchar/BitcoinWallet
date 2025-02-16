package ua.honchar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.honchar.domain.usecase.GetTransactionsUseCase
import ua.honchar.domain.usecase.SaveIncomeUseCase
import ua.honchar.ui.mvi.UiAction
import ua.honchar.ui.mvi.UiEffect
import ua.honchar.ui.mvi.UiState
import ua.honchar.ui.mvi.delegate.MVI
import ua.honchar.ui.mvi.delegate.mvi
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    override val uiState: StateFlow<UiState> = getTransactionsUseCase().map {
        UiState(transactions = it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), UiState())

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.OnAddClick -> saveIncomeUseCase(1000.0)
                UiAction.OnAddTransactionClick -> TODO()
                UiAction.OnNewCoinsSaveClick -> TODO()
            }
        }
    }
}