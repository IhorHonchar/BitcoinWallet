package ua.honchar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.honchar.common.onFailure
import ua.honchar.common.onSuccess
import ua.honchar.domain.model.Transaction
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.domain.usecase.FilterToValidAmountUseCase
import ua.honchar.domain.usecase.GetTransactionsUseCase
import ua.honchar.domain.usecase.SaveTransactionUseCase
import ua.honchar.domain.model.TransactionContainer
import ua.honchar.domain.usecase.GetCurrencyRateUseCase
import ua.honchar.domain.usecase.GetCurrentBalanceUseCase
import ua.honchar.presentation.mvi.WalletAction
import ua.honchar.presentation.mvi.WalletEffect
import ua.honchar.presentation.mvi.WalletState
import ua.honchar.ui.mvi.delegate.MVI
import ua.honchar.ui.mvi.delegate.mvi
import javax.inject.Inject

private const val STOP_TIME_1_HOUR = 1_000L * 60 * 60

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val saveTransactionUseCase: SaveTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val filterToValidAmount: FilterToValidAmountUseCase,
    private val currentBalanceUseCase: GetCurrentBalanceUseCase,
    private val currencyRateUseCase: GetCurrencyRateUseCase
) : ViewModel(), MVI<WalletState, WalletAction, WalletEffect> by mvi(WalletState()) {

    private val _uiState: MutableStateFlow<WalletState> = MutableStateFlow(WalletState())
    override val uiState: StateFlow<WalletState> = _uiState.onStart {
        getCurrencyRate()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIME_1_HOUR), WalletState())

    val transactions = getTransactionsUseCase().cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            currentBalanceUseCase().collect {
                updateUiState { copy(balance = it.toString()) }
            }
        }
    }

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

    override fun updateUiState(block: WalletState.() -> WalletState) {
        _uiState.update(block)
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
            val amount = uiState.value.dialogState.text.toDoubleOrNull()
            if (amount == null) {
                emitUiEffect(WalletEffect.ShowSnackBar("Enter amount"))
                return@launch
            }
            if (amount == .0) {
                emitUiEffect(WalletEffect.ShowSnackBar("Amount has to be bigger 0"))
                return@launch
            }
            saveTransactionUseCase(amount, TransactionCategory.REFILL)
        }
    }

    private fun getCurrencyRate() = viewModelScope.launch(Dispatchers.IO) {
        currencyRateUseCase()
            .onSuccess { rate ->
                val dotIndex = rate.indexOf('.')
                val updatedRate = if (dotIndex == -1) {
                    rate
                } else {
                    val fourSymbAfterDot =
                        (dotIndex + 4).takeIf { it <= rate.length } ?: rate.lastIndex
                    rate.removeRange(fourSymbAfterDot, rate.lastIndex)
                }
                updateUiState {
                    copy(currencyRate = "1 ₿ = $updatedRate $")
                }
            }
            .onFailure {
                val errorMessage = it.message ?: "Some error"
                emitUiEffect(WalletEffect.ShowSnackBar(errorMessage))
            }
    }
}