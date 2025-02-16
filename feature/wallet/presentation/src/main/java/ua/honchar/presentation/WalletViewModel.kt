package ua.honchar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.honchar.domain.usecase.GetTransactionsUseCase
import ua.honchar.domain.usecase.SaveIncomeUseCase
import ua.honchar.presentation.mvi.UiAction
import ua.honchar.presentation.mvi.UiEffect
import ua.honchar.presentation.mvi.UiState
import ua.honchar.ui.mvi.delegate.MVI
import ua.honchar.ui.mvi.delegate.mvi
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        viewModelScope.launch {
            getTransactionsUseCase().collect { transactions ->
                updateUiState {
                    copy(
                        balance = transactions.sumOf { it.amount }.toString(),
                        transactions = transactions
                    )
                }
            }
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.OnAddClick -> showIncomeDialog()
                UiAction.OnAddTransactionClick -> emitUiEffect(UiEffect.NavigateToAddTransaction)
                UiAction.OnSaveClick -> saveIncome()
                UiAction.OnCancelClick ->hideIncomeDialog()
                is UiAction.EnteredIncome -> validateInputText(uiAction.value)
            }
        }
    }

    private fun validateInputText(text: String) {
        var hasDot = false
        val validatedText = text.filter {
            val isValidSymbol = it.isDigit() || (it == '.' && !hasDot)
            if (it == '.') {
                hasDot = true
            }
            isValidSymbol
        }
        updateUiState {
            copy(
                dialogState = dialogState.copy(text = validatedText)
            )
        }
    }

    private fun showIncomeDialog() {
        updateUiState {
            copy(dialogState = EnterIncomeDialogState(true))
        }
    }

    private fun hideIncomeDialog() {
        updateUiState {
            copy(dialogState = dialogState.copy(visible = false))
        }
    }

    private fun saveIncome() {
        hideIncomeDialog()
        viewModelScope.launch {
            val amount = uiState.value.dialogState.text.toDoubleOrNull() ?: .0
            saveIncomeUseCase(amount)
        }
    }
}