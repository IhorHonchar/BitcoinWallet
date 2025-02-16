package ua.honchar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.honchar.common.onFailure
import ua.honchar.common.onSuccess
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.domain.usecase.FilterToValidAmountUseCase
import ua.honchar.domain.usecase.SaveTransactionUseCase
import ua.honchar.presentation.mvi.AddTransactionAction
import ua.honchar.presentation.mvi.AddTransactionEffect
import ua.honchar.presentation.mvi.AddTransactionState
import ua.honchar.ui.mvi.delegate.MVI
import ua.honchar.ui.mvi.delegate.mvi
import javax.inject.Inject

@HiltViewModel
internal class AddTransactionViewModel @Inject constructor(
    private val filterToValidAmountUse: FilterToValidAmountUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : ViewModel(),
    MVI<AddTransactionState, AddTransactionAction, AddTransactionEffect> by mvi(AddTransactionState()) {

    override fun onAction(action: AddTransactionAction) {
        when (action) {
            AddTransactionAction.OnAddClick -> addTransaction()
            AddTransactionAction.OnBackClick -> backClick()
            is AddTransactionAction.OnEnteredAmount -> amountEntered(action.value)
            is AddTransactionAction.OnExpandedChange -> updateUiState {
                copy(expandedDropMenu = action.value)
            }

            is AddTransactionAction.OnSelectedCategory -> updateUiState {
                copy(
                    selectedCategory = action.category,
                    expandedDropMenu = false
                )
            }
        }
    }

    private fun backClick() = viewModelScope.launch {
        emitUiEffect(AddTransactionEffect.NavigateBack)
    }

    private fun amountEntered(amount: String) {
        val filteredRes = filterToValidAmountUse(amount)
        updateUiState {
            copy(amount = filteredRes)
        }
    }

    private fun addTransaction() = viewModelScope.launch(Dispatchers.IO) {
        val amount = getAmount() ?: return@launch
        val category = getCategory() ?: return@launch
        saveTransactionUseCase(amount, category)
            .onSuccess {
                backClick()
            }.onFailure {
                val message = it.message ?: "Some error"
                emitUiEffect(AddTransactionEffect.ShowSnackBar(message))
            }
    }

    private suspend fun getAmount(): Double? {
        val amount = uiState.value.amount.toDoubleOrNull()
        if (amount == null) {
            emitUiEffect(AddTransactionEffect.ShowSnackBar("Enter amount field"))
            return null
        }
        if (amount == .0) {
            emitUiEffect(AddTransactionEffect.ShowSnackBar("Amount has to be bigger 0"))
            return null
        }
        return amount
    }

    private suspend fun getCategory(): TransactionCategory? {
        val category = uiState.value.selectedCategory
        if (category == null) {
            emitUiEffect(AddTransactionEffect.ShowSnackBar("Select category"))
            return null
        }
        return category
    }
}