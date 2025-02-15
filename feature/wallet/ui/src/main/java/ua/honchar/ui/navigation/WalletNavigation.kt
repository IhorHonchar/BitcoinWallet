package ua.honchar.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ua.honchar.ui.WalletScreen

const val WALLET_SCREEN = "wallet-screen"

fun NavGraphBuilder.wallet() {
    composable(WALLET_SCREEN) {
        WalletScreen()
    }
}