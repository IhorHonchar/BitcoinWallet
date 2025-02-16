package ua.honchar.presentation

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ua.honchar.domain.model.Transaction
import ua.honchar.ui.R
import ua.honchar.presentation.mvi.UiAction
import ua.honchar.presentation.mvi.UiEffect
import ua.honchar.presentation.mvi.UiState
import ua.honchar.ui.theme.BitcoinWalletTheme
import ua.honchar.ui.theme.Typography

@Composable
internal fun WalletScreen(
    state: UiState,
    onAction: (UiAction) -> Unit,
) {
    EnterIncomeDialogRoot(state.dialogState, onAction)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
        ) {
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
                        onAction(UiAction.OnAddClick)
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
                    // todo implement
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
                .background(Color.Blue)
                .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(Color.Gray),
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
        WalletScreen(UiState(), {})
    }
}