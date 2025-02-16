package ua.honchar.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ua.honchar.ui.WalletScreen
import ua.honchar.ui.WalletViewModel

const val WALLET_SCREEN = "wallet-screen"

fun NavGraphBuilder.wallet() {
    composable(WALLET_SCREEN) {
        val viewModel: WalletViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val uiEffect = viewModel.uiEffect
        WalletScreen(
            state = uiState,
            effect = uiEffect,
            onAction = viewModel::onAction
        )
    }
}