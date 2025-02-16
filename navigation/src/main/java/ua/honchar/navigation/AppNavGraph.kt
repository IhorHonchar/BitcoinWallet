package ua.honchar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ua.honchar.presentation.navigation.ADD_TRANSACTION_SCREEN
import ua.honchar.presentation.navigation.WALLET_SCREEN
import ua.honchar.presentation.navigation.addTransaction
import ua.honchar.presentation.navigation.wallet

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = WALLET_SCREEN
    ) {
        wallet(navigateToAddTransaction = {
            navController.navigate(ADD_TRANSACTION_SCREEN)
        })
        addTransaction(navigateBack = {
            navController.popBackStack()
        })
    }
}