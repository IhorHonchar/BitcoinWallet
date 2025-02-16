package ua.honchar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ua.honchar.presentation.mvi.WalletAction
import ua.honchar.ui.theme.BitcoinWalletTheme
import ua.honchar.ui.theme.Typography

data class EnterIncomeDialogState(
    val visible: Boolean = false,
    val text: String = ""
)

@Composable
fun EnterIncomeDialogRoot(state: EnterIncomeDialogState, onAction: (WalletAction) -> Unit) {
    if (state.visible) {
        EnterIncomeDialog(state, onAction)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterIncomeDialog(state: EnterIncomeDialogState, onAction: (WalletAction) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()

    val afterHide: (callback: () -> Unit) -> Unit = { callback ->
        scope.launch { modalBottomSheetState.hide() }.invokeOnCompletion {
            if (!modalBottomSheetState.isVisible) {
                callback()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onAction(WalletAction.OnCancelClick)
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        shape = RectangleShape
    ) {
        Content(
            state = state,
            cancel = {
                afterHide { onAction(WalletAction.OnCancelClick) }
            },
            save = {
                afterHide {
                    onAction(WalletAction.OnSaveClick)
                }
            },
            entered = {
                onAction(WalletAction.EnteredIncome(it))
            }
        )
    }
}

@Composable
private fun Content(
    state: EnterIncomeDialogState,
    cancel: () -> Unit,
    save: () -> Unit,
    entered: (String) -> Unit
) {
    Column {
        TextField(
            value = state.text,
            onValueChange = entered,
            placeholder = {
                Text(
                    text = "Enter income",
                    style = Typography.labelMedium,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        )

        Row(modifier = Modifier.padding(top = 12.dp)) {
            Button(
                onClick = cancel,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Cancel",
                    style = Typography.titleMedium
                )
            }
            Button(
                onClick = save,
                enabled = state.text.isNotEmpty(),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Save",
                    style = Typography.titleMedium
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DialogPreview() {
    BitcoinWalletTheme { Content(EnterIncomeDialogState(true), {}, {}, {}) }
}