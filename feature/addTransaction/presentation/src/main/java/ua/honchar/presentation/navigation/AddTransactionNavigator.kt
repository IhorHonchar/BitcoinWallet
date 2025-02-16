package ua.honchar.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ua.honchar.presentation.AddTransactionScreen
import ua.honchar.presentation.AddTransactionViewModel
import ua.honchar.presentation.mvi.AddTransactionEffect

const val ADD_TRANSACTION_SCREEN = "add-transaction-screen"

fun NavGraphBuilder.addTransaction(
    navigateBack: () -> Unit
) {
    composable(ADD_TRANSACTION_SCREEN) {
        val viewModel: AddTransactionViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val effect = viewModel.uiEffect

        AddTransactionScreen(
            state = state,
            effect = effect,
            onAction = viewModel::onAction,
            navigateBack = navigateBack
        )
    }
}