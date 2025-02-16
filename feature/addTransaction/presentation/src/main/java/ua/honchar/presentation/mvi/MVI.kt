package ua.honchar.presentation.mvi

import ua.honchar.domain.model.TransactionCategory


data class AddTransactionState(
    val amount: String = "",
    val selectedCategory: TransactionCategory? = null,
    val expandedDropMenu: Boolean = false,

    )

sealed interface AddTransactionAction {
    data object OnAddClick: AddTransactionAction
    data object OnBackClick: AddTransactionAction
    data class OnEnteredAmount(val value: String): AddTransactionAction
    data class OnSelectedCategory(val category: TransactionCategory): AddTransactionAction
    data class OnExpandedChange(val value: Boolean): AddTransactionAction
}

sealed interface AddTransactionEffect {
    data object NavigateBack: AddTransactionEffect
    data class ShowSnackBar(val message: String): AddTransactionEffect
}