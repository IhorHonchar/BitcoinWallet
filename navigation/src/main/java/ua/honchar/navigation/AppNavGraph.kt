package ua.honchar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ua.honchar.ui.navigation.WALLET_SCREEN
import ua.honchar.ui.navigation.wallet

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
        wallet()
    }
}