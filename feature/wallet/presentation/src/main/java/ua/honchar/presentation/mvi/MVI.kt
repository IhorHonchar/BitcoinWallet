package ua.honchar.presentation.mvi

import ua.honchar.domain.model.Transaction
import ua.honchar.presentation.EnterIncomeDialogState

data class WalletState(
    val balance: String = "0",
    val currencyRate: String = "",
    val transactions: List<Transaction>? = null,
    val dialogState: EnterIncomeDialogState = EnterIncomeDialogState()
)

sealed interface WalletAction {
    data object OnAddTransactionClick: WalletAction
    data object OnAddClick: WalletAction
    data object OnSaveClick: WalletAction
    data object OnCancelClick: WalletAction
    data class EnteredIncome(val value: String): WalletAction
}

sealed interface WalletEffect {
    data object NavigateToAddTransaction: WalletEffect
}