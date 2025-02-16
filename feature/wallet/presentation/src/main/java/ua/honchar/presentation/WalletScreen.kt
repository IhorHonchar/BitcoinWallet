package ua.honchar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ua.honchar.domain.model.Transaction
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.presentation.mvi.WalletAction
import ua.honchar.presentation.mvi.WalletState
import ua.honchar.ui.theme.BitcoinWalletTheme
import ua.honchar.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WalletScreen(
    state: WalletState,
    onAction: (WalletAction) -> Unit,
) {
    EnterIncomeDialogRoot(state.dialogState, onAction)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = state.currencyRate,
                    style = Typography.titleMedium,
                    modifier = Modifier.align(Alignment.End)
                )
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = state.balance,
                        style = Typography.displayLarge,
                    )
                    IconButton(
                        onClick = {
                            onAction(WalletAction.OnAddClick)
                        },
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddCircle,
                            contentDescription = "add",
                            modifier = Modifier
                                .size(Typography.displayLarge.fontSize.value.dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        onAction(WalletAction.OnAddTransactionClick)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 5.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(
                            text = "Add transaction",
                            style = Typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            Text(
                text = "Transactions :",
                style = Typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    vertical = 5.dp,
                    horizontal = 3.dp
                ),
            ) {
                items(items = state.transactions.orEmpty()) {
                    TransactionItem(it)
                }
            }
        }
    }

}

@Composable
private fun TransactionItem(transaction: Transaction) {
    val (icon, color) = if (transaction.amount > 0) Icons.Outlined.KeyboardArrowUp to Color.Green
    else Icons.Outlined.KeyboardArrowDown to Color.Red
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            text = transaction.amount.toString(),
            style = Typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = transaction.category,
            style = Typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Text(
            text = transaction.time,
            style = Typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@PreviewLightDark
@Composable
private fun ScreenPreview() {
    BitcoinWalletTheme {
        WalletScreen(
            WalletState(
                transactions = listOf(
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "25.10.2022",
                        time = "14:56"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "25.10.2022",
                        time = "14:54"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "25.10.2022",
                        time = "13:56"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "25.10.2022",
                        time = "12:56"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "24.10.2022",
                        time = "14:56"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "24.10.2022",
                        time = "14:54"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "24.10.2022",
                        time = "13:56"
                    ),
                    Transaction(
                        300.0,
                        TransactionCategory.ELECTRONICS(),
                        date = "24.10.2022",
                        time = "12:56"
                    )
                )
            ), {})
    }
}