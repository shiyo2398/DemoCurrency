package com.shiyo.featurecurrency.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shiyo.coreresources.model.CurrencyType
import com.shiyo.coreresources.model.NavigationAction
import com.shiyo.featurecurrency.data.local.CurrencyViewState
import com.shiyo.featurecurrency.ui.list.CurrencyList
import com.shiyo.featurecurrency.ui.list.EmptyState
import com.shiyo.featurecurrency.ui.list.ErrorState
import com.shiyo.featurecurrency.ui.list.LoadingState
import com.shiyo.featurecurrency.ui.list.SearchTopBar

@Composable
fun CurrencyListScreen(
    state: CurrencyViewState,
    type: CurrencyType = CurrencyType.ALL, // Default type for Previews
    onEvent: (CurrencyType) -> Unit = {}, // Event handler
    onSearchQuery: (query: String) -> Unit = {},
    onNavigate: (action: NavigationAction) -> Unit ={}
) {

    // Trigger the event for loading currencies based on the type
    LaunchedEffect(type) {
        onEvent(type) // Convert type to event and pass it
    }

    Scaffold(
        topBar = {
            Column {
                SearchTopBar(onSearchQuery = onSearchQuery, onNavigate = onNavigate)
                HorizontalDivider(color = Color.Gray.copy(alpha = 0.15f), thickness = 1.dp)
            }
        }
    ) { innerPadding ->
        when (state) {
            is CurrencyViewState.Loading -> LoadingState()

            is CurrencyViewState.Error -> ErrorState(message = state.message)

            is CurrencyViewState.Empty -> EmptyState(innerPadding)

            is CurrencyViewState.Success -> CurrencyList(
                currencies = state.currencies,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyListScreenPreviewLoading() {
    CurrencyListScreen(
        type = CurrencyType.CRYPTO,
        state = CurrencyViewState.Loading,
        onEvent = {}, // No-op for Preview
        onSearchQuery = {}, // No-op for Preview
        onNavigate = {} // No-op for Preview
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyListScreenPreviewError() {
    CurrencyListScreen(
        type = CurrencyType.ALL,
        state = CurrencyViewState.Error("Something went wrong"),
        onEvent = {}, // No-op for Preview
        onSearchQuery = {}, // No-op for Preview
        onNavigate = {} // No-op for Preview
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyListScreenPreviewEmpty() {
    CurrencyListScreen(
        type = CurrencyType.CRYPTO,
        state = CurrencyViewState.Empty,
        onEvent = {}, // No-op for Preview
        onSearchQuery = {}, // No-op for Preview
        onNavigate = {} // No-op for Preview
    )
}



