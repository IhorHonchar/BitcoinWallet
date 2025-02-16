package ua.honchar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import ua.honchar.domain.model.Transaction
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.domain.usecase.FilterToValidAmountUseCase
import ua.honchar.domain.usecase.GetTransactionsUseCase
import ua.honchar.domain.usecase.SaveTransactionUseCase
import ua.honchar.domain.model.TransactionContainer
import ua.honchar.domain.usecase.GetCurrentBalanceUseCase
import ua.honchar.presentation.mvi.WalletAction
import ua.honchar.presentation.mvi.WalletEffect
import ua.honchar.presentation.mvi.WalletState
import ua.honchar.ui.mvi.delegate.MVI
import ua.honchar.ui.mvi.delegate.mvi
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val saveTransactionUseCase: SaveTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val filterToValidAmount: FilterToValidAmountUseCase,
    private val currentBalanceUseCase: GetCurrentBalanceUseCase,
) : ViewModel(), MVI<WalletState, WalletAction, WalletEffect> by mvi(WalletState()) {

    init {
        viewModelScope.launch {
            currentBalanceUseCase().collect {
                updateUiState { copy(balance = it.toString()) }
            }
        }
    }

    val transactions = getTransactionsUseCase().cachedIn(viewModelScope)

    override fun onAction(action: WalletAction) {
        viewModelScope.launch {
            when (action) {
                WalletAction.OnAddClick -> showIncomeDialog()
                WalletAction.OnAddTransactionClick -> emitUiEffect(WalletEffect.NavigateToAddTransaction)
                WalletAction.OnSaveClick -> saveIncome()
                WalletAction.OnCancelClick -> hideIncomeDialog()
                is WalletAction.EnteredIncome -> validateInputText(action.value)
            }
        }
    }

    private fun validateInputText(text: String) {
        val validatedText = filterToValidAmount.invoke(text)
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
            saveTransactionUseCase(amount, TransactionCategory.REFILL)
        }
    }
}