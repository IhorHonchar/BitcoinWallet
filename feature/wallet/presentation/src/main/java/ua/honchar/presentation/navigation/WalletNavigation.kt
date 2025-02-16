package ua.honchar.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ua.honchar.presentation.WalletScreen
import ua.honchar.presentation.WalletViewModel
import ua.honchar.presentation.mvi.UiEffect

const val WALLET_SCREEN = "wallet-screen"

fun NavGraphBuilder.wallet(
    navigateToAddTransaction: () -> Unit
) {
    composable(WALLET_SCREEN) {
        val viewModel: WalletViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val effect = viewModel.uiEffect

        LaunchedEffect(key1 = Unit) {
            effect.collect {
                when (it) {
                    UiEffect.NavigateToAddTransaction -> navigateToAddTransaction()
                }
            }
        }

        WalletScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}