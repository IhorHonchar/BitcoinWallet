package ua.honchar.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ua.honchar.domain.model.TransactionCategory
import ua.honchar.presentation.mvi.AddTransactionAction
import ua.honchar.presentation.mvi.AddTransactionEffect
import ua.honchar.presentation.mvi.AddTransactionState
import ua.honchar.ui.theme.BitcoinWalletTheme
import ua.honchar.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTransactionScreen(
    state: AddTransactionState,
    effect: Flow<AddTransactionEffect>,
    onAction: (AddTransactionAction) -> Unit,
    navigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        effect.collect {
            when (it) {
                AddTransactionEffect.NavigateBack -> navigateBack()
                is AddTransactionEffect.ShowSnackBar -> snackbarHostState.showSnackbar(
                    message = it.message,
                    withDismissAction = true
                )
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add transaction",
                        modifier = Modifier.padding(start = 20.dp),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(AddTransactionAction.OnBackClick) },
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(AddTransactionAction.OnAddClick)
                }
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = null)
                    Text(
                        text = "Add",
                        style = Typography.titleMedium
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 12.dp)
        ) {
            TextField(
                value = state.amount,
                onValueChange = {
                    onAction(AddTransactionAction.OnEnteredAmount(it))
                },
                placeholder = {
                    Text(
                        text = "Enter amount",
                        style = Typography.labelMedium,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            val options = TransactionCategory.costCategories()
            ExposedDropdownMenuBox(
                expanded = state.expandedDropMenu,
                onExpandedChange = {
                    onAction(AddTransactionAction.OnExpandedChange(it))
                },
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    value = state.selectedCategory?.invoke().orEmpty(),
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = {
                        Text("Category")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.expandedDropMenu)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = state.expandedDropMenu,
                    onDismissRequest = {
                        onAction(AddTransactionAction.OnExpandedChange(false))
                    },
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            onClick = {
                                onAction(AddTransactionAction.OnSelectedCategory(option))
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ScreenPreview() {
    BitcoinWalletTheme {
        AddTransactionScreen(AddTransactionState(), emptyFlow(), {}) { }
    }
}
