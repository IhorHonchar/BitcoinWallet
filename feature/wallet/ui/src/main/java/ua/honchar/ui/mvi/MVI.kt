package ua.honchar.ui.mvi

import ua.honchar.domain.model.Transaction

data class UiState(
    val balance: String = "0",
    val currencyRate: String? = null,
    val transactions: List<Transaction>? = null,
)

sealed interface UiAction {
    data object OnAddTransactionClick: UiAction
    data object OnAddClick: UiAction
    data object OnNewCoinsSaveClick: UiAction
}

sealed interface UiEffect {
    data object NavigateToAddTransaction: UiEffect
}