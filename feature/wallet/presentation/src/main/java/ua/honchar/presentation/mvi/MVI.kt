package ua.honchar.presentation.mvi

import ua.honchar.domain.model.Transaction
import ua.honchar.presentation.EnterIncomeDialogState

data class UiState(
    val balance: String = "0",
    val currencyRate: String = "",
    val transactions: List<Transaction>? = null,
    val dialogState: EnterIncomeDialogState = EnterIncomeDialogState()
)

sealed interface UiAction {
    data object OnAddTransactionClick: UiAction
    data object OnAddClick: UiAction
    data object OnSaveClick: UiAction
    data object OnCancelClick: UiAction
    data class EnteredIncome(val value: String): UiAction
}

sealed interface UiEffect {
    data object NavigateToAddTransaction: UiEffect
}